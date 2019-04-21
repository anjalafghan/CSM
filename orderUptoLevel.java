package order;
import java.util.Scanner;
public class orderUptoLevel {
    public static void main(String[] args) {

        new Inventory().calculateOrderUpToLevel();
    }
}
 class Inventory {

     private int inventory_level;
     private int periodic_review_length;
     private int order_item;
     private int order_day;
     private int cycle;
     private int temporary_lead_time;
     private int temporary_shortage_quantity;
     private int temporary_shortageQuantity;
     private int[] beginning_inventory;
     private int[] end_inventory;
     private int[] shortage_quantity;
     private int[] ordered_quantity;
     private int[] lead_time;
     private int[][] daily_demands;
     private int i;
     private int j;

     public void calculateOrderUpToLevel() {
        Scanner scanner=new Scanner(System.in);
         initializeVariables();
         getInput(scanner);
         calculateOrderUptoLevel();
     }

     private void initializeVariables() {
         order_item = 0;
         order_day = 0;
         temporary_shortage_quantity = 0;
         temporary_shortageQuantity = 0;
         beginning_inventory = new int[100];
         end_inventory = new int[100];
         shortage_quantity = new int[100];
         ordered_quantity = new int[100];
         lead_time = new int[100];
         daily_demands = new int[50][50];
     }

     private void getInput(Scanner scanner) {
         getInventoryLevelPeriodicLengthBeginningInventory(scanner);
         getOrderItemOrderDay(scanner);
         getNoOfCycles(scanner);
         getDailyDemands(scanner);
         getLeadTime(scanner);
     }

     private void getLeadTime(Scanner scanner) {
         System.out.println("Enter lead time");
         for(i =0; i < cycle; i++)
             lead_time[i]=scanner.nextInt();
     }

     private void getDailyDemands(Scanner scanner) {
         System.out.println("Enter daily demands");
         for(i =0; i < cycle; i++)
             for(j =0; j < periodic_review_length; j++)
                 daily_demands[i][j]=scanner.nextInt();
     }

     private void getNoOfCycles(Scanner scanner) {
         System.out.println("Enter the no of simulation cycles");
         cycle =scanner.nextInt();
     }

     private void getOrderItemOrderDay(Scanner scanner) {
         System.out.println("enter the order items if any and expected order arrival day");
         order_item =scanner.nextInt();
         order_day =scanner.nextInt();
     }

     private void getInventoryLevelPeriodicLengthBeginningInventory(Scanner scanner) {
         System.out.println("Enter Inventory level(M), periodic review length(N),Beginning inventory or ending inventory of last week(BI) ");
         inventory_level = scanner.nextInt();
         periodic_review_length =scanner.nextInt();
         beginning_inventory[0]=scanner.nextInt();
     }

     private void calculateOrderUptoLevel() {
         System.out.println("CYCLE"+"\t"+"DAY"+"\t"+"BI"+"\t"+"DD"+"\t"+"EI"+"\t"+"SQ"+"\t"+"OQ"+"\t"+"LT"+"\n");
         for(i =0; i < cycle; i++)
             calculateInventory(i);
     }

     private void calculateInventory(int i) {
         temporary_lead_time =0;
         for(j =0; j < periodic_review_length; j++)
         {
             if(order_item > 0)
                 beginning_inventory[order_day] += order_item;
             if(beginning_inventory[j] <= daily_demands[i][j])
                 initializeInventory(i, j);
             else
                 calculateEndInventory(i, j);
             ordered_quantity[j]=0;
             beginning_inventory[j +1]= end_inventory[j];

             if(j == periodic_review_length - 1)
                 getOrderQuantity(i, j);
             if(shortage_quantity[j]>0)
                 getShortageQuantity(j);

             else
                 temporary_shortage_quantity = 0;
             System.out.print(""+(i+1)+"\t"+""+(j +1)+"\t"+""+ beginning_inventory[j]+"\t"+""+ daily_demands[i][j]+"\t"+""+ end_inventory[j]+"\t"+""+ shortage_quantity[j]+"\t"+""+ ordered_quantity[j]+"\t"+""+ temporary_lead_time +"\n");
         }

         System.out.println("order of "+ order_item +" items will arrive after "+ order_day +" days.");
         System.out.println();
     }

     private void getShortageQuantity(int j) {
         if(j>1)
             temporary_shortage_quantity += shortage_quantity[j]+ shortage_quantity[j-1];
            else
             temporary_shortage_quantity += shortage_quantity[j];
     }

     private void getOrderQuantity(int i, int j) {
         ordered_quantity[j] = inventory_level - end_inventory[j]+ shortage_quantity[j];

         if(ordered_quantity[j] < inventory_level)
             order_item = ordered_quantity[j];

         else{
             order_item = inventory_level;
             ordered_quantity[j] = inventory_level;
         }
         order_day = lead_time[i];
         temporary_lead_time = lead_time[i];
         beginning_inventory[0]= end_inventory[j];
     }

     private void calculateEndInventory(int i, int j) {
         end_inventory[j]= beginning_inventory[j]- daily_demands[i][j]- temporary_shortageQuantity;
         shortage_quantity[j]=0;
         temporary_shortageQuantity =0;
     }

     private void initializeInventory(int i, int j) {
         end_inventory[j]=0;
         shortage_quantity[j]= daily_demands[i][j]- beginning_inventory[j]+ temporary_shortageQuantity;
         temporary_shortageQuantity = shortage_quantity[j];
     }
 }
