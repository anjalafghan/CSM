package multiserver;
import java.util.Scanner;

class multiServer{
    public static void main(String[] args){
        MultiServerHelper.calculate();

    }

    private static class MultiServerHelper {

        private static int[] interArrivalTime;
        private static int[] arrivalTime;
        private static int[] Able_available;
        private static int[] Baker_available;
        private static char[] server;
        private static int[] serviceTime;
        private static int[] serviceBegin;
        private static int[] ableServiceEnd;
        private static int[] bakerServiceEnd;
        private static int[] customerWaitingTime;
        private static int[] customerTotalTimeinSystem;
        private static int numberOfCustomers;
        private static int currentCustomer;
        private static Scanner scanner;

        private static void calculate() {

            scanner = new Scanner(System.in);
            System.out.println("Enter the no of customers");
            numberOfCustomers = scanner.nextInt();

            //Array declarations
            interArrivalTime = new int[numberOfCustomers];
            arrivalTime = new int[numberOfCustomers];
            Able_available = new int[numberOfCustomers];
            Baker_available = new int[numberOfCustomers];
            server = new char[numberOfCustomers];
            serviceTime = new int[numberOfCustomers];
            serviceBegin = new int[numberOfCustomers];
            ableServiceEnd = new int[numberOfCustomers];
            bakerServiceEnd = new int[numberOfCustomers];
            customerWaitingTime = new int[numberOfCustomers];
            customerTotalTimeinSystem = new int[numberOfCustomers];

            for(currentCustomer =0; currentCustomer < numberOfCustomers; currentCustomer++){
                getInterArrivalTime();
                getServiceTime();
                calculateArrivalTime();

                if(currentCustomer == 0){
                    calculateForFirstCustomer();
                }
                else{

                    calulateAbleAndBakerAvailable();
                    selectServer();
                    calculateServiceBegin();
                    calculateAbleAndBakerServiceEnd();
                    calculateCustomerWaitingTime();
                    calculateCustomerTotalTimeinSystem();
                }

            }

            printTable();
        }

        private static void calculateCustomerTotalTimeinSystem() {
            if(server[currentCustomer]=='A')
                customerTotalTimeinSystem[currentCustomer] = ableServiceEnd[currentCustomer] - arrivalTime[currentCustomer];
            else
                customerTotalTimeinSystem[currentCustomer] = bakerServiceEnd[currentCustomer] - arrivalTime[currentCustomer];
        }

        private static void calculateCustomerWaitingTime() {
            customerWaitingTime[currentCustomer] = serviceBegin[currentCustomer] - arrivalTime[currentCustomer];
        }

        private static void calculateAbleAndBakerServiceEnd() {
            if(server[currentCustomer]=='A'){
                ableServiceEnd[currentCustomer] = serviceBegin[currentCustomer] + serviceTime[currentCustomer];
                bakerServiceEnd[currentCustomer] = 0;
            }
            else{
                bakerServiceEnd[currentCustomer] = serviceBegin[currentCustomer] + serviceTime[currentCustomer];
                ableServiceEnd[currentCustomer] = 0;
            }
        }

        private static void calculateServiceBegin() {
            if((Able_available[currentCustomer]> arrivalTime[currentCustomer])&&(Baker_available[currentCustomer]> arrivalTime[currentCustomer])){
                if(Able_available[currentCustomer]< Baker_available[currentCustomer])
                    serviceBegin[currentCustomer] = Able_available[currentCustomer];
                else
                    serviceBegin[currentCustomer] = Baker_available[currentCustomer];
            }
            else{
                serviceBegin[currentCustomer] = arrivalTime[currentCustomer];
            }
        }

        private static void selectServer() {
            int temp1;
            int temp2;//Filling Server columns
            if(Able_available[currentCustomer]<= arrivalTime[currentCustomer])
                server[currentCustomer] = 'A';
            else if(Baker_available[currentCustomer]<= arrivalTime[currentCustomer])
                server[currentCustomer] = 'B';
            else if(Able_available[currentCustomer]== Baker_available[currentCustomer])
                server[currentCustomer] = 'A';

            else{
                temp1 = Able_available[currentCustomer] - arrivalTime[currentCustomer];
                temp2 = Baker_available[currentCustomer] - arrivalTime[currentCustomer];
                if(temp1<temp2)
                    server[currentCustomer] = 'A';
                else
                    server[currentCustomer] = 'B';
            }

            //Resetting value of temp1 and temp2
            temp1 = 0;
            temp2 = 0;
        }

        private static void calulateAbleAndBakerAvailable() {
            if(ableServiceEnd[currentCustomer -1]> bakerServiceEnd[currentCustomer -1]){
                Able_available[currentCustomer] = ableServiceEnd[currentCustomer -1];
                Baker_available[currentCustomer] = Baker_available[currentCustomer -1];
            }
            else{
                Baker_available[currentCustomer] = bakerServiceEnd[currentCustomer -1];
                Able_available[currentCustomer] = Able_available[currentCustomer -1];
            }
        }

        private static void calculateForFirstCustomer() {
            Able_available[currentCustomer] = 0;
            Baker_available[currentCustomer] = 0;
            server[currentCustomer] = 'A';
            serviceBegin[currentCustomer] = 0;
            ableServiceEnd[currentCustomer] = serviceBegin[currentCustomer] + serviceTime[currentCustomer];
            bakerServiceEnd[currentCustomer] = 0;
            customerWaitingTime[currentCustomer] = 0;
            customerTotalTimeinSystem[currentCustomer] = serviceTime[currentCustomer];
        }

        private static void printTable() {
            System.out.println("Cu \t  IAT\t  AT\t  Avi \t Bvi \t Sr \t ST \t SB \t Ase \t Bse \t CWT \t CTTS ");
            for(currentCustomer =0; currentCustomer < numberOfCustomers; currentCustomer++){
                System.out.println((currentCustomer +1) + "\t\t" + interArrivalTime[currentCustomer] + "\t\t" + arrivalTime[currentCustomer] + "\t\t" + Able_available[currentCustomer] + "\t\t" + Baker_available[currentCustomer] + "\t\t" + server[currentCustomer] + "\t\t" + serviceTime[currentCustomer] + "\t\t" + serviceBegin[currentCustomer] + "\t\t" + ableServiceEnd[currentCustomer] + "\t\t" + bakerServiceEnd[currentCustomer] + "\t\t" + customerWaitingTime[currentCustomer] + "\t\t" + customerTotalTimeinSystem[currentCustomer]);
            }
        }

        private static void calculateArrivalTime() {
            if((currentCustomer ==0)||(currentCustomer ==1))
                arrivalTime[currentCustomer] = interArrivalTime[currentCustomer];
            else
                arrivalTime[currentCustomer] = interArrivalTime[currentCustomer] + arrivalTime[currentCustomer -1];
        }

        private static void getServiceTime() {
            System.out.println("Enter the Service time of customer" + (currentCustomer +1) + " : ");
            serviceTime[currentCustomer] = scanner.nextInt();
        }

        private static void getInterArrivalTime() {
            System.out.println("Enter the Interarrival time of customer" + (currentCustomer +1) + " : ");
            interArrivalTime[currentCustomer] = scanner.nextInt();
        }
    }
}