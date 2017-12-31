package db.bigdata.webserver.app;


public class Application {

    public static void main(String[] args) throws Exception {

        String[] clients = new String[0];

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--clients":
                case "-c":
                    i++;
                    clients = args[i].split(",");
                    break;
                default:
                    System.out.println("Unknown Argument:" + args[i]);
                    System.exit(1);
            }
        }

        Bootstrap.init(clients);
        new JettyServer().start();
    }

}
