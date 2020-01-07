package fr.emse.iot.local;


public class MainClass {
    public static void main(String[] args) throws Exception{
        System.out.println("I' m happy to day ");

        System.out.println("Subscriber ....");
        MqttManager b = new MqttManager();
        b.subscribe();

        /*System.out.println("Choose Publisher, tape 0 or Subscriber 1 ?");
        int choice = new Scanner(System.in).nextInt();
        if (choice==0)
        {
            System.out.println("Publisher ....");
            MqttManager a = new MqttManager();
            while (true) a.publish();
        }
        else
            {
                System.out.println("Subscriber ....");
                MqttManager b = new MqttManager();
                b.subscribe();
            }

         */


    }
}


