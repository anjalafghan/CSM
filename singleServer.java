package com.company;
import java.util.*;
class singleServer {

    private static Scanner scanner;

    public static void main(String args[])
    {
        SingleServerHelper.runSingleServer();
    }

    private static class SingleServerHelper {

        private static int[] Customer;
        private static int[] InterArrivalTime;
        private static int[] ServiceTime;
        private static int[] ArrivalTime;
        private static int[] ServiceBegin;
        private static int[] ServiceEnd;
        private static int[] CustomerWaitingTime;
        private static int[] CustomerTotalTimeInService;
        private static int[] SystemIdle;
        private static int numberOfCustomers;
        private static int currentCustomer;

        private static void runSingleServer() {
            scanner = new Scanner(System.in);
            System.out.println("Enter no. of Customer: ");
            numberOfCustomers          = scanner.nextInt();
            Customer                   = new int[numberOfCustomers];
            InterArrivalTime           = new int[numberOfCustomers];
            ServiceTime                = new int[numberOfCustomers];
            ArrivalTime                = new int[numberOfCustomers];
            ServiceBegin               = new int[numberOfCustomers];
            ServiceEnd                 = new int[numberOfCustomers];
            CustomerWaitingTime        = new int[numberOfCustomers];
            CustomerTotalTimeInService = new int[numberOfCustomers];
            SystemIdle                 = new int[numberOfCustomers];

            for(currentCustomer = 0; currentCustomer < numberOfCustomers; currentCustomer++){
                getInterArrivalTimeAndServiceTime();
                calculateArrivalTimeServiceBeginServiceEnd();
                calculateCustomerTotalTimeInServiceAndCustomerWaitingTime();
                calculateSystemIdle();
            }

            printTable();
        }

        private static void printTable() {
            System.out.println("Cust \t IA \t AT \t ST \t SB \t SE \t CWT \t CTTS \t SI ");

            for(currentCustomer = 0; currentCustomer < numberOfCustomers; currentCustomer++)
            {
                System.out.println(Customer[currentCustomer] +"\t\t" + InterArrivalTime[currentCustomer] + "\t\t" + ArrivalTime[currentCustomer] + "\t\t" + ServiceTime[currentCustomer] + "\t\t" + ServiceBegin[currentCustomer] + "\t\t" + ServiceEnd[currentCustomer] + "\t\t" + CustomerWaitingTime[currentCustomer] + "\t\t" + CustomerTotalTimeInService[currentCustomer]+"\t\t"+ SystemIdle[currentCustomer]);
            }
        }

        private static void calculateArrivalTimeServiceBeginServiceEnd() {
            if(currentCustomer == 0){
                calculateArrivalTimeServiceBeginServiceEndForFirstCustomer();
            }
            else{
                calculateArrivalTime();
                calculateServiceBegin();
                calculateServiceEnd();
            }
        }

        private static void calculateServiceEnd() {
            ServiceEnd[currentCustomer] = ServiceBegin[currentCustomer] + ServiceTime[currentCustomer];
        }

        private static void calculateServiceBegin() {

            if(ArrivalTime[currentCustomer] >= ServiceEnd[currentCustomer -1])
                ServiceBegin[currentCustomer] = ArrivalTime[currentCustomer];

            else
                ServiceBegin[currentCustomer] = ServiceEnd[currentCustomer -1];

        }

        private static void calculateArrivalTime() {
            ArrivalTime[currentCustomer] = ArrivalTime[currentCustomer -1] + InterArrivalTime[currentCustomer];
        }

        private static void calculateArrivalTimeServiceBeginServiceEndForFirstCustomer() {
            ArrivalTime[currentCustomer] = 0;
            ServiceBegin[currentCustomer] = 0;
            ServiceEnd[currentCustomer] = ServiceTime[currentCustomer];
        }

        private static void calculateSystemIdle() {
            if(currentCustomer == 0)
                SystemIdle[currentCustomer] = 0;
            else
                SystemIdle[currentCustomer] = ServiceBegin[currentCustomer] - ServiceEnd[currentCustomer -1];
        }

        private static void calculateCustomerTotalTimeInServiceAndCustomerWaitingTime() {
            CustomerWaitingTime[currentCustomer] = ServiceBegin[currentCustomer] - ArrivalTime[currentCustomer];
            CustomerTotalTimeInService[currentCustomer] = ServiceEnd[currentCustomer] - ArrivalTime[currentCustomer];
        }

        private static void getInterArrivalTimeAndServiceTime() {
            System.out.println("Enter Inter-Arrival Time and Service Time for Customer " + currentCustomer +": ");
            Customer[currentCustomer] = currentCustomer + 1;
            InterArrivalTime[currentCustomer] = scanner.nextInt();
            ServiceTime[currentCustomer] = scanner.nextInt();
        }
    }
}

