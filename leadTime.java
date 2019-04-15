package leadTime;

import java.util.Scanner;

public class leadTime {

    public static void main(String[] args) {


        LeadTimeDemandHelper.calculateLeadTime();
    }

    private static class LeadTimeDemandHelper {

        private static int leadTime;
        private static int leadTimeDemand;
        private static int numberOfCycles;
        private static int currentDemand;
        private static int[] demand;
        private static int currentCycle;

        private static void calculateLeadTime() {
            leadTimeDemand = 0;
            demand = new int[10];

            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the number of cycles");
            numberOfCycles = scanner.nextInt();

            for (currentCycle = 1; currentCycle <= numberOfCycles; currentCycle++){

                getLeadTime(scanner, currentCycle);
                calculateLeadTimeDemand(scanner);
                printTable(currentCycle);

            }
        }

        private static void printTable(int currentCycle) {
            System.out.println("Cycle\tL.T\tD\tLTD");
            System.out.print(currentCycle+"\t"+ leadTime +"\t");

            if (currentDemand >1){
                calculateCurrentDemand();
            }

            else {
                System.out.print(demand[1]);
            }
            System.out.println("Total:\t\t\t"+ leadTimeDemand);
            leadTimeDemand =0;
            System.out.println("------------------------------------------");
        }

        private static void calculateCurrentDemand() {
            for (int currentDemand = 1; currentDemand <= leadTime; currentDemand++){
                System.out.println(demand[currentDemand]);
                if (currentDemand!= leadTime)
                    System.out.print("\t\t");
            }
        }

        private static void getLeadTime(Scanner scanner, int i) {
            System.out.println("Enter the lead time for the cycle "+ i +":");
            leadTime = scanner.nextInt();
        }

        private static void calculateLeadTimeDemand(Scanner scanner) {
            System.out.println("Enter the demand for the cycle "+ currentCycle +":");

            for (currentDemand =1; currentDemand <= leadTime; currentDemand++){
                demand[currentDemand] = scanner.nextInt();
                leadTimeDemand = leadTimeDemand + demand[currentDemand];
            }
        }
    }
}
