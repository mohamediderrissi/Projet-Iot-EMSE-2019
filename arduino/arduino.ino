#include <string.h>

#include <SPI.h>
#include <stdlib.h>

#include <WiFiNINA.h>
#include <MQTT.h>
#include <ArduinoHttpClient.h>

/* We need objects to handle WiFi connectivity
 */
WiFiClient wifi_client ;
MQTTClient mqtt_client ;
// publish a body to this topic means that we send a vehicle to a chosen light rand.
String send_vehicle = "/light/";
// subscribe to this topic means that we handle the light number 2
String handle_light = "/light/2";
// subscribe to this topic means that we are entering an intersection choosen rand.
String intersection_topic = "/intersection/1";
unsigned long lastMillis = 0;


/* Variables to tell:
 *  1. which WiFi network to connect to
 *  2. what are the MQTT broker IP address and TCP port
 */
const char* wifi_ssid     = "lab-iot-1"; // TODO: change this
const char* wifi_password = "lab-iot-1"; // TODO: change this
const char* mqtt_host = "max.isasecret.com" ;
const uint16_t mqtt_port =  1723 ;  
int potentiometerValue;
String OnOFF = "false";
/* In which pins is my buttons plugged? */
#define PIN_BUTTON 2

#define POTENTIOMETER_PIN A4
/* What light is on? */
#define LIGHT_OFF 0
#define LIGHT_GREEN 1
#define LIGHT_YELLOW 2
#define LIGHT_RED 3

/* States */
#define STATE_GREEN 0 // stays LIGHT_GREEN
#define STATE_TURN_RED 1 // transition LIGHT_YELLOW, then LIGHT_RED
#define STATE_RED 2 // stays LIGHT_RED
#define STATE_TURN_GREEN 3 // transition LIGHT_YELLOW, then LIGHT_GREEN
#define STATE_YELLOW 4 // state where LIGHT_YELLOW and LIGHT_OFF alternate

/* Timing constants */
#define BLINK_T 500 // Duration of the LIGHT_YELLOW and LIGHT_OFF when they alternate
#define TRANSITION_T 1000 // Transition duration between two colors when turning red or turning green
#define RED_T 8000 // duration of the red state
#define GREEN_T 8000 // duration of the green state
#define WINDOW_T 200 // we consider that the button cannot be pressed too often, else it is an error.

/* Variables */
int light; // variable for the light that is on
int state; // variable for the state we are in
uint32_t t; // will store the current time, or more precisely the time when we entered the loop
uint32_t t_change; // next time there will be a change
uint32_t t_button; // the last time the button was pressed
int count; // number of times the button has been pressed
int count_intersection = 0; // count number of vehicle that passed from intersection (1)
String my_topic = "";
const char serverAddress[] = "idrissi.cleverapps.io";  // server address
int port = 80;


HttpClient clientHttp = HttpClient(wifi_client, serverAddress, port);


 
void sendValues(String topic,String msg) {
       mqtt_client.publish(topic, msg) ;
}



void carEvent() {
  int accident, carDirection;
   // sendValues();
  switch(light) {
    case LIGHT_RED:
      count++;
      Serial.print("A car enters the queue.");
      break;
    case LIGHT_GREEN:
      Serial.print("A car passes.");
      if(count>0) {
        count--;
      }
      carDirection = (int)(rand() / (double)RAND_MAX * (4 - 1));
       my_topic = send_vehicle + carDirection;
     
      if(carDirection != 2){
       sendValues(my_topic, "GREEN: a vehicle passed through direction:");
       Serial.println(carDirection);
      }else{
        Serial.println(carDirection);
        Serial.println("Car passing from light 2");
        sendValues(my_topic,"tow_truck"); // change this to json object
       // TODO: count number of passing vehicle from this intersection
       
      }
      break;
    case LIGHT_YELLOW:
      Serial.print("A car passes when the light is yellow, caution!");
      if(count>0) {
        count--;
      }
      accident = (int)(rand() / (double)RAND_MAX * (10 - 1));
      if(accident == 0) {
        Serial.println("\n - Oh no! There is a little accident!");
        state = STATE_YELLOW;
      } else {
        carDirection = (int)(rand() / (double)RAND_MAX * (4 - 1));
        my_topic = send_vehicle + carDirection;
        sendValues(my_topic,"tow_truck");
//        Serial.print("NO ACCIDENT: It takes direction, send vehicle to a topic ");
//        Serial.println(carDirection);
      }
      break;
    case LIGHT_OFF:
      Serial.print("A car passes when the light is off, caution!");
      if(count > 0) {
        count--;
      }
      accident = (int)(rand() / (double)RAND_MAX * (10 - 1));
      if(accident == 0) {
        Serial.print(" Oh! There is a little accident!");
        state = STATE_YELLOW;
      } else {

        carDirection = (int)(rand() / (double)RAND_MAX * (4 - 1));
        my_topic = send_vehicle + carDirection;
        sendValues(my_topic,"tow_truck");

//        Serial.print(" It takes direction ");
//        Serial.println(carDirection);
      }
      break;
  }
  Serial.print("The queue contains ");
  Serial.print(count);
  Serial.print(" cars \n");
}

void connect() {
  Serial.print("checking wifi...");
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(1000);
  }

  Serial.print("\nconnecting...");
  while (!mqtt_client.connect("arduino", "majinfo2019", "Y@_oK2")) {
    Serial.print(".");
    delay(1000);
  }

  Serial.println("\nconnected!");

  mqtt_client.subscribe(handle_light);
  mqtt_client.subscribe(intersection_topic);
//  mqtt_client.publish("/light/3","sssssssss");
  // client.unsubscribe("/hello");
}

void setup() {
  // monitoring via Serial is always nice when possible
  Serial.begin(9600) ;
  delay(100) ;
  Serial.println("Initializing...\n") ;

  // initialize the pins as input or output:

  pinMode(PIN_BUTTON, INPUT);
  pinMode(POTENTIOMETER_PIN, INPUT);
  light = LIGHT_OFF;
  state = STATE_GREEN;
  count = 0;
 
  // ADDED
  WiFi.begin(wifi_ssid, wifi_password) ;
  //"broker.shiftr.io"
  mqtt_client.begin("max.isasecret.com",1723, wifi_client) ;
  mqtt_client.onMessage(callback) ;
  connect();
    // Time begins now!
  t = millis() ;
  t_button = t;
 sendValues("/light/0","messageee 2 !!");
  // set up the lights
}

void loop() {
  mqtt_client.loop();
  if (!mqtt_client.connected()) {
    connect ();
  }
    potentiometerValue = analogRead(POTENTIOMETER_PIN);

  t = millis() ;
   delay(2000);
    

   
  if (digitalRead(PIN_BUTTON) == HIGH) {
    Serial.println("Button pressed !!");    
    Serial.print("potentiometer value: ");
    Serial.println(potentiometerValue);
    char str[12];
    sprintf(str, "%d", potentiometerValue); 
//     mqtt_client.publish("/toggle",str);
       switch18Hue(OnOFF);
       if(OnOFF == "true"){
        OnOFF = "false";
        }else{
           OnOFF = "true";
          }
       
    // char str[12];
    //sprintf(str, "%d", potentiometerValue);
      //     mqtt_client.publish("/toggle", str) ;

    // sendValues("/toggle", potentiometerValue.toString());
  }
}
char server[] = "192.168.1.131";


void switch18Hue(String OnOff){
    String contentType = "application/json";
    String pathSwitchLight="";
    if(OnOff == "true"){
      pathSwitchLight = "/api/lights/18/switchOff";
      }else{
       pathSwitchLight= "/api/lights/13/switchOn";
        }
 
    String data = "";
    clientHttp.put(pathSwitchLight, contentType, data);
    
    

  delay(2000);

  }



void callback(String &intopic, String &payload)
{
  /* There's nothing to do here ... as long as the module
   *  cannot handle messages.
   */
  
        Serial.println("incoming: " + intopic + " - " + payload);
    

  
   
  }
