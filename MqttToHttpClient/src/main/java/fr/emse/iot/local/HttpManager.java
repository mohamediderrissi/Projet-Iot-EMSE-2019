package fr.emse.iot.local;

import org.springframework.web.client.RestTemplate;

import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class HttpManager {
    final String HubIP = "http://192.168.1.131"+"/api/Yitxh42Ia-iXVorQ-IuQugZGW8aCVEfxX9w5jNLK/lights";


    RestTemplate restTemplate = new RestTemplate();

   // @RequestMapping(value = "/api/lights/" , method = RequestMethod.GET)
    public void getLights() throws TimeoutException
    {
        final String url = HubIP;
        String result = restTemplate.getForObject(url, String.class);
        System.out.println(result);
    }

   // @RequestMapping(value = "/setLightOff", method = RequestMethod.GET)
    public void setLightOff(String id) throws TimeoutException
    {
        //url = HubIP+"/<id>/state";

        String url = HubIP+"/"+id+"/state";
        System.out.println("setLightOff :: id of light is :"+id);
        String bodyRequest = "{\"on\": false  }";
        restTemplate.put(url,bodyRequest);
    }

  //  @RequestMapping(value = "/setLightOn", method = RequestMethod.GET)
    public void setLightOn(String id) throws TimeoutException
    {
        //url = HubIP+"/<id>/state";

        String url = HubIP+"/"+id+"/state";
        System.out.println("setLightOn :: id of light is :"+id);
        String bodyRequest = "{\"on\": true}";
        restTemplate.put(url,bodyRequest);
    }


    /**
     * The message recieved in the parameter of the function
     *  take the following pattern  :   " id \n value_brightness \n value_hue \n Light_Status"
     */
    public void setLightBrightnessOrHue(String message) throws TimeoutException
    {
        Scanner scanner = new Scanner(message);
        int id = Integer.valueOf(scanner.nextLine());

        System.out.println("setLightBrightnessOrHue :  id of light is :"+id);
        String url = HubIP+"/"+id+"/state";

        int bri = Integer.valueOf(scanner.nextLine());

        int hue = Integer.valueOf(scanner.nextLine());

        String s = scanner.nextLine();
        String bodyRequest;
        /**
         * We verified if the light status is on or off, so even if it's off, if we change the brightness or the hue
         * The light will be set to ON
         */
        if(s.contains("ON"))
        {
            bodyRequest = "{" + "\"hue\":" + hue + ",\"bri\":"+ bri +"}";
        }
        else {
            bodyRequest = "{ \"on\": true," + "\"hue\":" + hue + ",\"bri\":"+ bri +"}";
        }

        restTemplate.put(url,bodyRequest);
    }
}
