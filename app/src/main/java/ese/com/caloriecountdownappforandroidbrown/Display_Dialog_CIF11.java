package ese.com.caloriecountdownappforandroidbrown;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;


import androidx.fragment.app.DialogFragment;
import android.os.Bundle;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by lokeke on 04/11/2015.
 */
public class Display_Dialog_CIF11 extends DialogFragment
{
    private
    String Volume1 = "\n" +
            "ING,Tesco Cornish Pasty,200,234,31.5,,,9,31.5,2.3,1.25,N,1.5,2\n" +
            "ING,Cadbury egg,100,170,6,,,,,,,N,,\n" +
            "ING,Lindt Chocolate bunny solid 1.75 ounces,100,298,18,,,,,,,N,,\n" +
            "ING,Dove dark chocolate eggs 4,100,148,9.2,,,,,,,N,,\n" +
            "ING,Dove milk chocolate eggs 4,100,160,9.2,,,,,,,N,,\n" +
            "ING,Hershey candy coated chocolate eggs 4,100,92,4,,,,,,,N,,\n" +
            "ING,Jelly beans 20,100,160,,,,,,,,N,,\n" +
            "ING,Jelly bellys 20,100,80,,,,,,,,N,,\n" +
            "ING,Jordan Almonds 13,100,140,5,,,,,,,N,,\n" +
            "ING,Peanut M&M 0.7 ounces,100,110,5,,,,,,,N,,\n" +
            "ING,Peeps 5,100,160,0,,,,,,,N,,\n" +
            "ING,Reese's Peanut Butter Eggs 1,100,180,11,,,,,,,N,,\n" +
            "ING,Robins' egg (malted milk candy) 4,100,90,2.5,,,,,,,N,,\n" +
            "ING,Clementines,100,35,0,0,0,1,9,,0.001,N,1,\n" +
            "ING ,Bagel- Wholegrain,100,350,3,,,,,,,N,,\n" +
            "ING,Strawberry Frosted Doughnuts,100,240,,,,,,,,N,,\n" +
            "ING,Cream Cheese- reduced fat,100,225,12,,,,,,,N,,\n" +
            "ING,Bagel- Standard,100,150,,,,,,,,N,,\n" +
            "ING,Chicken - Grilled,100,186,,,,,,,,N,,\n" +
            "ING,Sirlion 12 oz,100,768,,,,,,,,N,,\n" +
            "ING,Black bean soup,100,189,,,,,,,,N,,\n" +
            "ING,Texas Rice,100,80,,,,,,,,N,,\n" +
            "ING,Chocolate - Drifters,52,487,22.8,,,3.9,66.5,,,N,,\n" +
            "ING,Tofu,100,63.5,,,,,,,,N,,\n" +
            "ING,Nik Naks,30,561,38.3,8.4,,,,,1,N,3,\n" +
            "ING,Special K Crackers,30,400,10,,,10,77,30,0.87,N,,\n" +
            "ING,Light Peanut Butter Fudge Ice Cream,100,260,10,5,,,36,20,,N,6,\n" +
            "ING,Bread - Finest Oatmeal,75,333.33,4.4,,,5.3,20.4,,,N,,\n" +
            "ING,Bread - Pitta - Garlic and Herb,100,145,1.2,,,5.8,26.8,,,N,,\n" +
            "ING,Bread - White - Meduim Sliced,75,259.67,0.8,,,4,17.7,,,N,,\n" +
            "ING,Bread - Multigrain Farmhouse,75,320,1.6,,,5,21.3,,,N,,\n" +
            "ING,Bread - Brown - Thick,75,266.67,1,,,4.8,17.2,,,N,,\n" +
            "ING,Bread - White - Thick,75,105,0.7,,,3.6,21,,,N,,\n" +
            "ING,Bread - Wholemeal - Meduim,75,80,0.8,,,4,14.1,,,N,,\n" +
            "ING,Bread - Wholemeal - Pitta,100,145,1.5,,,6.7,26.2,,,N,,\n" +
            "ING,Finest Chicken Kiev,249,212,11.2,4.8,,19.5,8.2,0.9,1,N,2.6,5.5\n" +
            "ING,Vegetable Broth,500,42,0.32,,,8.52,1.32,,,N,,\n" +
            "ING,Cereal Bars - Cranberry,27,389,1.3,,,1.7,20.7,,,N,,\n" +
            "ING,Cereal Bars - Light Choices Maple ,22,340,0.6,,,1,15.5,,,N,,\n" +
            "ING,Cereal Bars - Special Flake with Chocolate Chip,21,405,1.6,,,1.4,15.9,,,N,,\n" +
            "ING,Cereal Bars - special flake with cranberry,21,405,1.2,,,1.3,16.4,,,N,,\n" +
            "ING,cereal bars - special flake with peach and apricot,21,405,1.2,,,1.2,16.5,,,N,,\n" +
            "ING,cereal bars - strawberry and with chocolate cereal bar,21,405,1.7,,,1.3,16,,,N,,\n" +
            "ING,Braeburn Apple Loose,133,46,0.2,0,,0.3,10.8,10.8,0,Y,1.7,\n" +
            "ING,Bramley Cooking Apples Loose,133,38,8.9,0,,0.3,8.9,8.9,0,Y,1.6,\n" +
            "ING,Cox Apples Loose Class ,133,50,0.1,0,,0.4,11.8,11.8,0,Y,1.8,\n" +
            "ING,Disney Apples Pack Class ,110,50,0.1,0,,0.4,11.8,11.8,0,Y,1.8,\n" +
            "ING,Empire Apples Loose Class ,133,50,0.1,0,,0.4,11.8,11.8,0,Y,1.8,\n" +
            "ING,Jazz Apples Pack Class ,133,50,0.1,0,,0.4,11.8,11.8,0,Y,1.8,\n" +
            "ING,Gala Apples Pack Class ,133,50,0.1,0,,0.4,11.8,11.8,0,Y,1.8,\n" +
            "ING,Golden Delicious Apples Pack Class ,133,47,0.3,0,,0.3,10.8,10.8,0,Y,1.7,\n" +
            "ING,Heritage Apples Pack Class ,133,50,0.1,0,,0.4,11.8,11.8,0,Y,1.8,\n" +
            "ING,Market Value Apple Pack,133,50,0.1,0,,0.4,11.8,11.8,0,Y,1.8,\n" +
            "ING,Pink Lady Apples Loose Class ,133,50,0.1,0,,0.4,11.8,11.8,0,Y,1.8,\n" +
            "ING,Pink Lady Apples Pack Class ,133,47,0.3,0,,0.3,10.8,10.8,0,Y,1.7,\n" +
            "ING,Conference Pears Pack Class ,133,42,0.1,0,,0.3,10,10,0,Y,2.2,\n" +
            "ING,Green Pears Loose Class ,133,42,0.1,0,,0.3,10,10,0,Y,2.2,\n" +
            "ING,Kids Funsize Pears Pack Class ,100,42,0.1,0,,0.3,10,10,0,Y,2.2,\n" +
            "ING,Market Value Pears Pack Class ,100,60,0.3,0,,0.3,13.2,13.2,0,Y,2.4,\n" +
            "ING,Perfectly Ripe Pears Tray Pack,100,42,0.3,0,,0.3,10,10,0,Y,2.2,\n" +
            "ING,Ripe and Ready Conference Pears Tray Pack,100,36,0.1,0,,0.4,8.3,8.3,0,Y,2.2,\n" +
            "ING,Rocha Pears Loose,170,36,0.1,0,,0.4,8.3,8.3,0,Y,2.2,\n" +
            "ING,Clementines Pack,142,39,0.1,0,,0.9,8.7,8.7,0,Y,1.2,\n" +
            "ING,Oranges 4 Pack,100,39,0.1,0,,1.1,8.5,8.5,12.5,Y,1.7,\n" +
            "ING,Jaffa Orange Pack,198,39,0.1,0,,1.1,8.5,8.5,0,Y,1.7,\n" +
            "ING,Jaffa Tangerine Pack,142,39,0.1,0,,0.9,8.7,8.7,0,Y,1.2,\n" +
            "ING,Large Orange Pack,198,39,0.1,0,,1.1,8.5,8.5,0,Y,1.7,\n" +
            "ING,Loose Clementines Class ,142,39,0.1,0,,0.9,8.7,8.7,0,Y,1.2,\n" +
            "ING,Loose Lemons Class ,100,20,0.3,0.1,,1,3.2,3.2,0,Y,0,\n" +
            "ING,Loose Mandarins Class ,150,35,0,0,,0.7,8,8,0,Y,0.3,\n" +
            "ING,Loose Oranges Class 1 Each,198,39,0.1,0,,1.1,8.5,8.5,0,Y,1.7,\n" +
            "ING,Loose Yellow Grapefuit Class ,100,31,0,0,,0.8,6.8,6.8,0,Y,1.3,\n" +
            "ING,Mandarin Pack,150,37,0.1,0,,0.8,9,9,0,Y,1.3,\n" +
            "ING,Market Value Orange Pack,154,39,0.1,0,,1.1,8.5,8.5,0,Y,1.7,\n" +
            "ING,Minneola Pack,100,37,0.1,0,,0.9,8,8,0,Y,1.3,\n" +
            "ING,Disney Funsize Easy Peeler,55,39,0.1,0,,0.9,8.9,8.9,0,Y,1.2,\n" +
            "ING,Grapefruit 3 pack,100,31,0.1,0,,0.8,6.8,6.8,0,Y,1.3,\n" +
            "ING,Unwaxed Lemons Pack,100,20,0.3,0.1,,1,3.2,3.2,0,Y,0,1.85\n" +
            "ING,Banana Loose,155,80,0.17,0,,0,16.78,16.78,0.2,Y,0,0.17\n" +
            "ING,Market Value Bananas,100,100,0.3,0.1,,1.2,23.2,20.9,0,Y,1.1,1.43\n" +
            "ING,Green Seedless Grapes Class ,80,64,0.1,0,,0.4,15,15,0,Y,0.7,1.93\n" +
            "ING,Red Seedless Grape Punnet 500g Pack,80,65,0.1,0,,0.4,15,15,0,Y,0.7,2.29\n" +
            "ING,Blueberry,100,32,0.2,0,,0.9,6.9,6.9,0,Y,1.8,2.98\n" +
            "ING,Strawberry,100,27,0.3,0,,1.4,4.6,4.6,0,Y,2.5,2.99\n" +
            "ING,Raspberry,100,27,0.3,0,,1.4,4.6,4.6,0,Y,2.5,1.98\n" +
            "ING,Cherries,100,55,0.1,0,,0.9,11.5,11.5,0,Y,0.9,2.48\n" +
            "ING,Loose Plums,118,39,0.1,0,,0.6,9,9,0,Y,1.6,0.2\n" +
            "ING,Apricot,45,33,0.1,0,,0.9,7.2,7.2,0,Y,1.7,2.49\n" +
            "ING,Plum Tray Pack,118,39,0.1,0,,0.6,9,9,0,Y,1.6,2.29\n" +
            "ING,Figs 4 Pack,100,46,0.3,0,,1.3,9.5,9.5,0,Y,1.5,1.8\n" +
            "ING,Kiwi,125,52,0.5,0,,1.1,10.6,10.3,0,Y,1.9,1.99\n" +
            "ING,Giant Sharon Fruit,100,80,0,0,,0.8,18.6,18.6,0,Y,1.6,0.75\n" +
            "ING,Pineapple,250,44,0.2,0,,0.4,10.1,10.1,0,Y,1.2,1.5\n" +
            "ING,Lychee Pack,100,60,0.4,0,,0.9,12.6,12.6,0,Y,0.6,1.5\n" +
            "ING,Passion Fruit,100,36,0.3,0,,2.6,5.8,0,0,Y,3.3,1.35\n" +
            "ING,Perfectly Ripe Kiwi 4 pk,120,55,0.5,0.1,,1.1,10.6,10.3,0,Y,1.9,1.99\n" +
            "ING,Ripe and Ready Mango,100,65,0.2,0.1,,0.7,14.1,13.8,0,Y,2.6,1.75\n" +
            "ING,Mango,100,65,0.2,0.1,,0.7,14.1,13.8,0,Y,2.6,0.87\n" +
            "ING,Sharon Fruit 4 Pack,100,80,0,0,,0.8,18.6,18.6,0,Y,1.6,1.5\n" +
            "ING,Ripe Papaya Each,100,38,0.1,0,,0.5,8.8,8.8,0,Y,2.3,1.5\n" +
            "ING,Pomegrante Each,100,80,0.8,0.3,,1.2,16.5,14.9,0,Y,2.6,1.5\n" +
            "ING,Pomelos Each,100,65,0.5,0.2,,0.6,14.2,11,0,Y,5.2,1.5\n" +
            "ING,Cantaloupe Melon,100,20,0.1,0,,0.6,4.2,4.2,0,Y,1,1.9\n" +
            "ING,Galia Melon,100,20,0.1,0,,0.6,4.2,4.2,0,Y,1,1.9\n" +
            "ING,Pile De Sapo Melon,100,30,0.1,0,,0.6,6.6,6.6,0,Y,0.6,2.5\n" +
            "ING,Watemelon,100,33,0.3,0,,0.5,7.1,7.1,0,Y,0.1,2.99\n" +
            "ING,Organic Apple Pack,127,50,0.1,0,,0.4,11.8,11.8,0,Y,1.8,2.25\n" +
            "ING,Organic Blueberries,100,32,0.2,0,,0.6,6.9,6.9,0,Y,1.8,1.99\n" +
            "ING,Organic Dessert Apples Tray,133,50,0.1,0,,0.4,11.8,11.8,0,Y,1.8,2.49\n" +
            "ING,Organic Disney Banana Pack,100,100,0.3,0.1,,1.2,23.2,20.9,0,Y,0,1.29\n" +
            "ING,Organic Gala Apple Pack,100,50,0.1,0,,0.4,11.8,11.8,0,Y,1.8,2.49\n" +
            "ING,Organic Grapes 400g,80,100,0.1,0,,0.4,15.4,15.4,0,Y,0.7,2.49\n" +
            "ING,Organic Kiwi Fruit Pack,100,55,0.5,0.1,,1.1,10.60,10.3,0,Y,1.9,0.9\n" +
            "ING,Organic White Grape,80,70,0.1,0,,0.4,15.4,15.4,0,Y,0.7,1.99\n" +
            "ING,Organic unwaxed Lemoms,100,20,0.3,0.1,,1,3.2,2.2,0,Y,0,1\n" +
            "ING,Organic Strawberry,80,28,0.1,0,,0.8,6,6,0,Y,1.1,1.99\n" +
            "ING,Organic Soft Citrus Clementine,71,39,0.1,0,,0.9,8.7,8.7,0,Y,1.2,1.99\n" +
            "ING,Organic Red Grapefruit Each,330,35,0.1,0,,0.8,6.8,6.8,0,Y,1.3,0.77\n" +
            "ING,Organic Large Banana Pack,100,105,0.3,0.1,,1.2,23.2,20.9,0,Y,1.1,1.69\n" +
            "ING,Organic Limes Pack,100,9,0.3,0,,0.7,0.8,0.9,0,Y,2.8,1.38\n" +
            "ING,Organic Mango,100,65,0.2,0,,0.7,14.1,13.8,0,Y,2.9,1\n" +
            "ING,Organic Oranges Pack,100,39,0.1,0,,1.1,8.5,8.5,0,Y,1.7,1.75\n" +
            "ING,Organic Pear,100,42,0.1,0,,0.3,10,10,0,Y,2.2,2.49\n" +
            "ING,Organic Pear Polybag,100,36,0.1,0,,0.4,8.3,8.3,0,Y,2.2,1.49\n" +
            "ING,Organic Raspberry,80,565,0.3,0,,113,27,1.4,0,Y,2.5,2.99\n" +
            "ING,Organic Plums Punnet,100,39,0.1,0,,0.6,8.8,8.8,0,Y,1.6,2\n" +
            "ING,Florette Fruit Medley 170g,170,49,0.06,0,,0.6,10.5,9.4,0,Y,1.65,1.3\n" +
            "ING,Juicy Pineapple Chunks 400g,400,177,0.2,0,,0.4,9.6,9.6,0,Y,1.2,2\n" +
            "ING,Mouthwatering Melon 200g,200,27,0.2,0,,0.5,5.7,5.7,0,Y,0.6,1.25\n" +
            "ING,Exotic Fruit Frenzy 300g,300,100,0.2,0,,0.7,9.5,9.4,0,Y,1.6,2\n" +
            "ING,Juicy Berry Burst 100g,100,47,0.1,0,,0.6,11,11,0,Y,1.1,1.25\n" +
            "ING,Fresh Seasonal Snack Pack,80,36,0.1,0,,0.4,8.3,8.3,0,Y,2.2,0.5\n" +
            "ING,Fresh Pineapple Lolly ,80,44,0.2,0,,0.4,10.1,10.1,0,Y,2,0.5\n" +
            "ING,Fresh Grape Snack Pack,80,65,0.1,0,,0.3,15.4,15.4,0,Y,0.7,0.5\n" +
            "ING,Fresh Classic Fruit Collection,300,43,0.1,0,,0.5,10.1,10,0,Y,1.4,2\n" +
            "ING,Fresh Apple Snack Pack,80,55,0.1,0,,0.3,12.3,12.3,0,Y,1.8,0.5\n" +
            "ING,Fresh Apple and Grape Snack Pack,80,55,0.1,0,,0.3,12.3,12.3,0,Y,2.6,0.5\n" +
            "ING,Pomegrante Solo ,100,55,0.2,0,,1.3,11.2,11.2,0,Y,3.4,1.25\n" +
            "ING,Fruity Fingers,100,55,0.2,0,,1.3,11.2,11.2,0,Y,3.4,3\n" +
            "ING,Juicy Fruity Twist,170,42,0.2,0,,0.4,9.6,9.6,0,Y,1.2,1.25\n" +
            "ING,Melon and Grapes Duo,400,33,0.1,0,,0.6,7.4,7.4,0,Y,0.8,2\n" +
            "ING,Rhubarb Pack,100,8,0.1,0,,0.9,0.8,0.8,0.8,Y,1.4,2.98\n" +
            "ING,Brazil Nuts,25,700,69.8,16.4,,14.1,3.1,2.4,0,Y,4.3,1.99\n" +
            "ING,Cashew Nuts,16,580,48.2,8.3,,17.7,18.1,4.6,0,Y,3.2,1.99\n" +
            "ING,Nuts and Dried Apricots,15,475,36.4,8.6,,8.4,28.4,11.6,0,Y,6,1.99\n" +
            "ING,Peanuts and Raisins,12.5,485,30.1,5.7,,17.7,35.8,24.8,0,Y,5.3,1\n" +
            "ING,Kids Fruit a Day 10 Pack,25,295,0.5,0.1,,2.6,69.6,66,0,Y,4.2,1.99\n" +
            "ING,Muscatel Raisins,25,290,0.4,0.1,,2.1,69.3,69.3,0.2,Y,2,1.5\n" +
            "ING,Organic Almonds,25,614,55.8,4.4,,21.1,6.9,4.2,0,Y,7.4,2.19\n" +
            "ING,Organic Mixed Nuts,50,660,64,8.7,,15.9,4.9,3.3,0,Y,5.5,2.49\n" +
            "ING,Organic Roasted Pistachios,100,610,46,5.6,,21.4,27.7,7.8,0,Y,10.3,1.69\n" +
            "ING,Organic Walnuts,100,690,68.5,5.6,,14.7,3.3,2.6,0,Y,3.5,1.7\n" +
            "ING,Halawi Dates,227,275,0.2,0,,2.3,65.5,65.5,0,Y,4.3,1\n" +
            "ING,Organic Medjool Dates 200g,80,310,0.2,0,,1.8,75,66.5,6,Y,6.7,1.97\n" +
            "ING,Organic Pine Nuts,25,690,68.6,4.6,,14,4,3.9,0,Y,1.9,2.59\n" +
            "ING,Almonds 175g,15,614,55.8,4.4,,21.1,6.9,4.2,0,Y,7.4,1.99\n" +
            "ING,Almonds and Raisins 150g,25,420,22.6,1.8,,9.7,44.3,43.3,0,Y,6.9,1.75\n" +
            "ING,Berry Mix 125g,25,265,0.7,0,,3.3,60.6,59.7,0,Y,7.5,1.5\n" +
            "ING,Blueberries 100g,25,330,1,0.2,,2,77.9,77.5,0,Y,3.2,1.99\n" +
            "ING,Brazil Nuts 175g,75,683,68.2,16.4,,14.1,3.1,2.4,0,Y,4.3,1.99\n" +
            "ING,Breakfast Topper 200g,16.5,441,24.8,2.4,,9.6,44.9,39.4,0,Y,5.6,2.5\n" +
            "ING,Cashew Nuts 300 g,25,580,48.2,8.3,,17.7,18.1,4.6,0,Y,3.2,3.25\n" +
            "ING,Wholefoods Country Mix 100g,50,505,37.9,4.8,,13,27.9,19.4,0,Y,6.3,1.5\n" +
            "ING,Cran and Macadamia 160g,25,490,31.3,4.8,,3.2,48,40.7,0,Y,6.5,1.99\n" +
            "ING,Cranberries 100g,25,355,0.8,0,,0.3,86.8,68,0,Y,4.6,1\n" +
            "ING,Dried Apricot Ready To Eat 250g,100,170,0.6,0,,4,36.5,36.5,0,Y,6.3,1.99\n" +
            "ING,Dried Cherries 100g,25,350,1.1,0.4,,0.2,84.3,66.5,0,Y,2.1,1.99\n" +
            "ING,Mixed Nuts 300g,25,659,64,8.7,,15.9,4.9,3.3,0,Y,5.5,3.15\n" +
            "ING,Medjool Dates 180g,10,290,0.2,0.1,,3.3,68,68,0,Y,4,1.88\n" +
            "ING,Medjool Date and Walnut 200g,20,335,7.7,0.7,,4.6,60.9,60.9,0,Y,3.9,2.75\n" +
            "ING,Monkey Nuts 500g,25,566,46,8.7,,25.6,12.5,6.2,0,Y,6.2,1.25\n" +
            "ING,Natural Apricot srte 250g,30,170,0.6,0,,4,36.5,36.5,0,Y,6.3,2.09\n" +
            "ING,Organic Dried Mango 100g,25,330,1.2,0.2,,2.2,77,47.9,0,Y,5.4,1.5\n" +
            "ING,Peacan Nuts 150g,33,695,70.1,5.7,,9.2,5.8,4.3,0,Y,4.7,1.99\n" +
            "ING,Pistachio Nuts 150g,25,610,52.8,5.8,,22.5,10.2,6.2,0,Y,10.7,1.99\n" +
            "ING,Prunes 250g,100,150,0.4,0,,2.5,34,34,0,Y,5.7,1.8\n" +
            "ING,Pumpkin Seeds 125g,25,585,45.8,8.6,,24.5,17.8,1,0,Y,3.9,0.75\n" +
            "ING,Raisin Mix 150g,25,305,1,0.4,,2.7,70.7,68.6,0,Y,5.9,1\n" +
            "ING,Roasted Soya Nuts 100g,25,440,25.9,4,,37,14.1,4.4,0.3,Y,15.7,0.75\n" +
            "ING,Roasted Marconas Almonds 175g,15,595,52.8,4,,22.1,7.4,4.9,0,Y,11.8,2.29\n" +
            "ING,Walnuts 220g,40,690,68.5,5.6,,14.7,3.3,2.6,0,Y,3.5,2.35\n" +
            "ING,Tropical Mix 150g,50,305,6.9,5.5,,3.4,56.9,52.8,0,Y,11.6,1.99\n" +
            "ING,Sunflower Seeds 125g,25,585,47.5,4.5,,19.8,18.6,1.6,0,Y,6,0.74\n" +
            "ING,Sultanas,25,275,0.8,0.5,,1.6,65.2,63.6,0.1,Y,3.6,1\n" +
            "ING,Savoury Seed Mix,50,555,45.6,5.4,,20.9,15,1.7,0.6,Y,6.4,0.75\n" +
            "ING,Salad Topper 100g,12.5,515,41.9,4.1,,18.5,16,3,0.4,Y,8.2,1.75\n" +
            "ING,Rooster Potatoes 2kg Pack,175,80,0.1,0,,1.8,17,0.7,0,Y,1.2,1.46\n" +
            "ING,Carolina Sweet Potatoes,175,87,0.3,0,,1.2,21.3,5.7,0,Y,2.4,2\n" +
            "ING,Charlotte Potatoes 1 Kg,250,75,0.3,0.2,,1.7,16.1,1.3,0,Y,1,1.25\n" +
            "ING,White Pototoes 5kg Tray,175,80,0.2,0,,2.1,17.2,0.6,0,Y,1.3,2.49\n" +
            "ING,Sweet Potatoes Loose Class 1,175,95,0.3,0.1,,1.2,21.3,5.7,0,Y,2.3,0.48\n" +
            "ING,Seasonal New Loose Potatoes,250,75,0.3,0.1,,1.7,16.1,1.3,0,Y,1,0.12\n" +
            "ING,Market Value White Potatoes 2.5 Kg ,200,80,0.2,0,,2.1,17.2,0.6,0,Y,1.3,0.99\n" +
            "ING,Finest Exquisa Pototoes 1 Kg,250,75,0.3,0.1,,1.7,16.1,1.3,0,Y,1,2\n" +
            "ING,Leeks Loose Class 2,100,23,0.5,0.1,,1.6,2.9,2.2,0,Y,2.2,0.53\n" +
            "ING,Loose Carrots Class 1,100,37,0.3,0.1,,0.6,7.9,7.4,0,Y,2.4,0.07\n" +
            "ING,Parsnips Loose Class 1,100,70,1.1,0.2,,1.8,12.5,5.7,0,Y,4.6,0.23\n" +
            "ING,Swede,100,26,0.3,0,,0.7,5,4.9,0,Y,1.9,0.89\n" +
            "ING,Garlic,100,105,0.6,0.1,,7.9,16.3,1.6,0,Y,4.1,1.19\n" +
            "ING,Baby Onions 500g,25,38,0.2,0,,1.2,7.8,5.5,0,Y,1.8,0.99\n" +
            "ING,Brown Onions,25,38,0.2,0,,1.3,7.8,5.5,0,Y,1.8,0.16\n" +
            "ING,Red Onions,25,38,0.2,0,,1.2,7.89,5.5,0,Y,1.4,0.21\n" +
            "ING,Spring Onion,25,25,0.5,0.1,,2,3,2.8,0,Y,1.5,0.5\n" +
            "ING,Spring Onion Jumbo,25,25,0.5,0.1,,2,3,2.8,0,Y,1.5,0.88\n" +
            "ING,Shallot Onions,25,21,0.2,0,,0.5,3.3,3.3,0,Y,1.4,0.22\n" +
            "ING,Baby Button Mushrooms,25,13,0.5,0.1,,1.8,0.4,0.2,0,Y,1.1,0.86\n" +
            "ING,Chestnut Mushrooms,25,13,0.5,0.1,,1.8,0.4,0.2,0,Y,1.1,1\n" +
            "ING,Chings Exotic Mushrooms,25,35,0.2,0.1,,2.8,5.6,5.3,0,Y,1,1.49\n" +
            "ING,Closed Mushroom,25,13,0.5,0.1,,1.8,0.4,0.2,0,Y,1.1,0.75\n" +
            "ING,Large Flat Mushrooms 250g,25,13,0.5,0.1,,1.8,0.4,0.2,0,Y,1.1,1.08\n" +
            "ING,Portobello Mushrooms 250g,25,13,0.5,0.1,,1.8,0.4,0.2,0,Y,1.1,1.69\n" +
            "ING,Shiitake Mushroom,25,13,0.5,0.1,,1.8,0.4,0.2,0,Y,1.1,1.69\n" +
            "ING,Loose Large Open Mushrooms,25,13,0.5,0.1,,1.8,0.4,0.2,0,Y,1.1,0.42\n" +
            "ING,Mixed Oyster Mushrooms ,25,13,0.5,0.1,,1.8,0.4,0.2,0,Y,1.1,1.17\n" +
            "ING,Market Value Broccoli Catch Weight,100,33,0.9,0.2,,4.4,1.8,1.5,0,Y,2.6,1.8\n" +
            "ING,Market Value Cabbage,100,27,0.4,0.1,,1.7,4.1,4,0,Y,2.4,0.59\n" +
            "ING,Red Cabbage,100,15,0.2,0,,1,2.2,2.1,0,Y,1.5,0.5\n" +
            "ING,Finest Tenderstem Broccoli 200g,100,31,0.2,0,,4.2,3.2,1.1,0,Y,3.1,1.57\n" +
            "ING,Cauliflower Extra Large,100,35,0.9,0.2,,3.6,3,2.5,0,Y,1.8,1.95\n" +
            "ING,Cauliflower  ,100,35,0.9,0.2,,3.6,3,2.5,0,Y,1.8,1.18\n" +
            "ING,White Cabbage,100,26,0.3,0,,0.7,5,4.9,0,Y,1.9,0.95\n" +
            "ING,Cabbage Sweetheart,100,33,1,3.4,,3,3.1,2.7,0,Y,3.4,0.78\n" +
            "ING,Cabbage Savoy,100,18,0.5,0,,1.1,2.2,2,0,Y,2,0.5\n" +
            "ING,Green Cabbage,100,16,0.4,0.1,,1,2.2,2,0,Y,1.8,0.78\n" +
            "ING,Fine Trimmed Beans 200g,80,25,0.5,0.1,,1.9,3.2,2.3,0,Y,2.2,1.5\n" +
            "ING,Finest Extra Fine Beans 250g,80,25,0.5,0.1,,0.9,3.2,2.3,0,Y,2.2,1.6\n" +
            "ING,Green Beans 240g,80,25,0.5,0.1,,0.9,3.2,2.3,0,Y,2.2,1\n" +
            "ING,Mangetout 200g,80,33,0.2,0,,3.6,4.2,3.4,0,Y,2.3,1.5\n" +
            "ING,Mangetout and Sugarsnaps,80,34,0.2,0,,3.5,4.6,3.5,0.5,Y,1.9,2\n" +
            "ING,Runner Beans,80,23,0.4,0.1,,1.6,3.2,2.8,0,Y,2,1\n" +
            "ING,Stringless Beans 225g,80,23,0.4,0.1,,1.6,3.2,2.8,0,Y,2,1.5\n" +
            "ING,Sugarsnaps 220g,80,34,0,0,,3.4,4.7,3.5,0,Y,1.5,1.5\n" +
            "ING,Baby Sprouts 300t,100,43,1.4,0.3,,3.5,4.1,3.1,0,Y,4.1,1\n" +
            "ING,Brussel Sprouts,100,43,1.4,0.3,,3.5,4.1,3.1,0,Y,4.1,1\n" +
            "ING,Fresh Greens 500g,100,20,0.7,0.1,,1.9,1.6,1.4,0,Y,2.6,0.68\n" +
            "ING,Fresh Greens Sliced 250g,100,33,1,0.1,,3,3.1,2.7,0,Y,3.4,0.91\n" +
            "ING,Kale Tenderleaf 200g,100,34,1.6,0.2,,3.4,1.4,1.3,0,Y,3.1,0.98\n" +
            "ING,Peeled Brussles 250g,100,43,1.4,0.3,,3.5,4.1,3.1,0,Y,4.1,1\n" +
            "ING,Spinach 260g,100,25,0.8,0.1,,2.8,1.6,1.5,0.3,Y,2.1,1.19\n" +
            "ING,Courgettes,100,18,0.4,0.1,,1.8,1.8,1.7,0,Y,0.9,0.35\n" +
            "ING,Butternut Squash,100,39,0.1,0,,1.1,8.3,4.5,0,Y,1.6,1.55\n" +
            "ING,Asparagus Tips and Babycorn 140g,100,25,0.5,0,,2.8,2.3,1.9,0,Y,1.8,1.5\n" +
            "ING,Baby Carrots 200g,100,31,0.5,0.1,,0.7,6,5.6,0,Y,2.4,1.5\n" +
            "ING,Baby Corn 175g,100,24,0.4,0,,2.5,2.7,1.9,0.3,Y,2,1.5\n" +
            "ING,Baby Corn Carrot Fine Bean and Sugarsnap,100,28,0.4,0.1,,2.2,4,3.3,0,Y,2.2,2.5\n" +
            "ING,Baby Courgettes 200g,100,18,0.4,0.1,,1.8,1.8,1.7,0,Y,0.9,1.5\n" +
            "ING,British Seasonal Pack 220,100,31,0.5,0.1,,0.7,6,5.6,0,Y,2.4,1.5\n" +
            "ING,Tenderstem and asparagus 190,100,29,0.4,0,,3.7,2.7,1.4,0,Y,2.7,2\n" +
            "ING,Peas and Babycorn 175g,100,113,0.2,0,,2.9,3.3,2.5,0,Y,2.1,1.5\n" +
            "ING,Stir Fry Vegetable Medley 240g,100,33,0.3,0,,2.8,4.7,3.6,0,Y,2.2,2\n" +
            "ING,Celery,180,7,0.2,0,,0.5,0.9,0.9,0,Y,1.1,0.78\n" +
            "ING,Celery Sticks 350g,80,7,0.2,0,,0.5,0.9,0.9,0.2,Y,1.1,0.98\n" +
            "ING,Sweet Celery Hearts,110,7,0.2,0,,0.5,0.9,0.9,0.2,Y,1.1,1.28\n" +
            "ING,Asparagus Tips  ,80,25,0.6,0.1,,2.9,2,1.9,0.3,Y,1.7,1.5\n" +
            "ING,Chicory,100,95,0.6,0,,19,2.8,0.7,0,Y,0.9,1\n" +
            "ING,Fennel,100,2,0.2,0,,0.9,1.8,1.7,0,Y,2.4,1.75\n" +
            "ING,Chillies,5,60,0.4,0.1,,2.6,10.7,4.5,0.8,Y,2.5,0.6\n" +
            "ING,Okra,100,32,1,0,,2.8,3,2.5,0,Y,4,1.49\n" +
            "ING,Root Ginger,5,40,0.6,0,,1.4,7.2,2.4,0,Y,0,0.68\n" +
            "ING,Pak choi,100,11,0.2,0,,1,1.4,1.4,0,Y,1.2,1\n" +
            "ING,Organic Asparagus Bundle 200g,25,25,0.6,0,,2.9,2,1.9,0,Y,1.7,1.99\n" +
            "ING,Organic Avocados Ripe and Ready,100,195,19.5,4.1,,1.9,1.9,0.5,0,Y,3.4,1.88\n" +
            "ING,Organic Baking Potatoes 1.2 kg,250,80,0.2,0,,2,17.3,0.6,0,Y,0,1.5\n" +
            "ING,Organic Baby Potato 640g,250,75,0.3,0,,1.7,16.1,1.3,0,Y,1,1\n" +
            "ING,Organic Broccoli,80,33,0.9,0.2,,4.4,1.8,1.5,0,Y,2.6,1.75\n" +
            "ING,Organic Button Mushroom 150g,80,13,0.5,0.1,,1.8,0.4,0.2,0,Y,1.1,0.98\n" +
            "ING,Organic Carrots 1.25kg,100,37,0.3,0.1,,0.6,7.9,7.4,0,Y,2.4,1.59\n" +
            "ING,Organic Cauliflower,100,35,0.9,0.2,,3.6,3,2.5,0,Y,1.8,1.69\n" +
            "ING,Organic Celery,100,7,0.2,0,,0.5,0.9,0.9,0.3,Y,1.1,1.2\n" +
            "ING,Organic Cherry on Vine Tomatoes 225g,60,18,0.3,0.1,,0.7,3.1,3.1,0,Y,1,1.58\n" +
            "ING,Organic Cherry Tomatoes 250g,60,18,0.3,0.1,,0.7,3.1,3.1,0,Y,0.6,1.35\n" +
            "ING,Organic Chestnut Mushrooms 200g,100,13,0.5,0.1,,1.8,0.4,0.2,0,Y,1.1,1.28\n" +
            "ING,Organic Large Tomatoes on the Vine,85,18,0.3,0.1,,0.7,3.1,3.1,0,Y,1,1.68\n" +
            "ING,Organic Fresh Beetroot,100,48,0.1,0,,2.3,9.5,8.8,0,Y,1.9,0.99\n" +
            "ING,Organic Fine Bean 160g,100,25,0.5,0.1,,1.9,3.2,2.3,0,Y,3,1.28\n" +
            "ING,Organic Curly Kale 200g,100,34,1.6,0.2,,3.4,1.4,1.3,0,Y,3.1,1.15\n" +
            "ING,Organic Leeks 400g,100,23,0.5,0.1,,1.6,2.9,2,5,Y,2.2,1.78\n" +
            "ING,Organic Lettuce,80,15,0.5,0.1,,0.8,1.7,1.7,0,Y,0.9,1\n" +
            "ING,Organic Little Gems,45,15,0.5,0.1,,0.8,1.7,1.7,0,Y,0.9,1.38\n" +
            "ING,Organic Mangetout 150g,75,33,0.2,0,,3.6,4.2,3.4,0,Y,4.2,1.5\n" +
            "ING,Organic Mushrooms 200g,100,13,0.5,0.1,,1.8,0.4,0.2,0,Y,1.1,0.9\n" +
            "ING,Organic New Potatoes 1.3kg,250,75,0.3,0,,1.7,16.1,1.3,0,Y,1,1\n" +
            "ING,Organic Onion 810g,100,38,0.2,0,,1.3,7.3,5.5,0,Y,1.8,1\n" +
            "ING,Organic Parsnips 500g,100,70,1.1,0.2,,1.8,12.5,5.7,0,Y,4.6,1.69\n" +
            "ING,Organic Peppers 2 Pack,100,16,0.3,,,0.8,2.6,2.4,0,Y,1.6,1.58\n" +
            "ING,Organic Portabello Mushrooms 200g,100,13,0.5,0.1,,1.8,0.4,0.2,0,Y,1.1,1.59\n" +
            "ING,Organic Spring Onions,100,25,0.5,0.1,,2,3,2.8,0,Y,1.5,0.7\n" +
            "ING,Organic Spinach,50,25,0.8,0.1,,2.8,1.6,1.5,0.05,Y,2.1,1.54\n" +
            "ING,Organic Savoy or Green Cabbage,100,27,0.4,0.1,,1.7,4.1,4.1,0,Y,2.4,1.27\n" +
            "ING,Organic Red or White Cabbage,100,27,0.4,0.1,,1.7,4.1,4,0,Y,2.4,1.23\n" +
            "ING,Organic Raw Beetroot,100,38,0.1,0,,1.7,7.6,7,0.2,Y,1.9,1.99\n" +
            "ING,Organic Portion Cucumber,80,10,0.1,0,,0.7,1.5,1.4,0,Y,0.6,0.73\n" +
            "ING,Organic Sugarnap Peas Pack,77,35,0.2,0,,3.4,5,3.7,0,Y,1.5,1.5\n" +
            "ING,Organic Swede,100,26,0.3,0,,0.7,5,4.9,0,Y,1.9,0.65\n" +
            "ING,Organic Sweet Pointed Peppers,100,33,0.4,0.1,,1,6.4,6.1,0,Y,1.6,1.58\n" +
            "ING,Organic Sweet Potatoes 750g,100,95,0.3,0,,1.2,21.3,5.7,0,Y,2.4,1.98\n" +
            "ING,Organic Sweetcorn,100,60,1.1,0.1,,2,9.9,1.2,0,Y,0.9,2.29\n" +
            "ING,Organic Sweetheart Cabbage,100,34,1.1,0.3,,3,3.1,2.7,0.1,Y,3.4,1.45\n" +
            "ING,Organic Unwashed babyleaf salad,40,20,0.6,0.1,,1.8,1.8,1.7,0,Y,1.4,1.29\n" +
            "ING,Organic Whole Cucumber,100,10,0.1,0,,0.7,1.5,1.4,0,Y,0.6,1.38\n" +
            "ING,Organic Watercress Spinach and Rocket 110g,37,25,0.9,0.2,,2.9,1.3,0.7,0.3,Y,1.9,1.7\n" +
            "ING,Organic Watercress ,50,23,1,0.3,,3,0.4,0.4,0.3,Y,1.5,1.49\n" +
            "ING,Organic Unwashed broccoli florets 250g,100,33,0.9,0.2,,4.4,1.8,1.5,0,Y,2.6,1.25\n" +
            "ING,Cooked Beetroot Vacuum Pack 250g,110,41,0.1,0,,1.8,8.4,6.4,0.4,Y,1.8,0.57\n" +
            "ING,East Anglian Traditional Beetroot 300g,90,38,0.1,0,,1.2,8.1,6.4,0.4,Y,2.2,0.6\n" +
            "ING,Sweetfire baby beetroot 180g,75,45,0.1,0,,0.6,10.4,10,0.3,Y,1.3,1.49\n" +
            "ING,Baby Avocados Pack,100,195,19.5,4.1,,1.9,1.9,0.5,0,Y,3.4,1.06\n" +
            "ING,Meduim Avocado,90,195,19.5,4.1,,1.9,1.9,0.5,0,Y,3.4,0.99\n" +
            "ING,Market Value Iceberg Lettue,80,13,0.3,0,,0.7,1.9,1.9,0,Y,0.6,0.88\n" +
            "ING,Chinese Leaf,80,11,0.2,0,,1,1.3,1.3,0,Y,1,1.18\n" +
            "ING,Crisp Continental Lettuce,100,15,0.5,0.1,,0.8,1.7,1.7,0,Y,0.9,1\n" +
            "ING,Finest Dolce Verde Lettuce,80,15,0.5,0.1,,0.8,1.7,1.7,0,Y,0.9,1.39\n" +
            "ING,Unwashed Rocket Salad,20,25,0.7,0.1,,2.6,2,2,0,Y,1.6,0.69\n" +
            "ING,Unwashed lambs lettuce 60g,30,15,0.5,0.1,,0.8,1.7,1.7,0,Y,0.9,0.79\n" +
            "ING,Salad Stir fry unwashed baby leaves,55,23,0.6,0.1,,2.9,1.5,1,0.3,Y,2.3,1\n" +
            "ING,Round Lettuce ,40,15,0.5,0.1,,0.8,1.7,1.7,0,Y,0.9,0.57\n" +
            "ING,Romaine Lettuce hearts twin pack,90,15,0.5,0.1,,0.8,1.7,1.7,0,Y,0.9,1.38\n" +
            "ING,Romaine Lettuce  ,80,15,0.5,0.1,,0.8,1.7,1.7,0,Y,0.9,1.19\n" +
            "ING,Little Gem Lettuce twin pack,45,15,0.5,0.1,,0.8,1.7,1.7,0,Y,0.9,0.88\n" +
            "ING,Iceberg Lettuce,80,13,0.3,0,,0.7,1.9,1.9,0,Y,0.6,0.98\n" +
            "ING,Beef Tomatoes,100,18,0.3,0.1,,0.7,3.1,3.1,0,Y,1,0.5\n" +
            "ING,Baby Plum tomatoes,60,18,0.3,0.1,,0.7,3.1,3.1,0,Y,1,1.08\n" +
            "ING,Cherry tomatoes 300g,60,19,0.4,0.1,,0.8,3,3,0,Y,1,0.98\n" +
            "ING,Tomatoes on the vine loose,70,18,0.3,0.1,,0.7,3.1,3.1,0,Y,1,0.12\n" +
            "ING,Sunstream Tomatoes on the vine,100,18,0.3,0.1,,0.7,3.1,3.1,0,Y,1,1.5\n" +
            "ING,Selection pack tomatoes 750g,80,18,0.3,0.1,,0.7,3.1,3.1,0,Y,1,1.48\n" +
            "ING,Perino Tomato on the vine,80,18,0.3,0.1,,0.7,3.1,3.1,0,Y,1,1.5\n" +
            "ING,Piccolo Cherry Tomato on the vine,60,18,0.3,0.1,,0.7,3.1,3.1,0,Y,1,1.48\n" +
            "ING,Sugar Drop Tomator on the vine,80,18,0.3,0.1,,0.7,3.1,3.1,0,Y,1,1.7\n" +
            "ING,Salad Tomatoes 6 Pack,70,18,0.3,0.1,,0.7,3.1,3.1,0,Y,1,0.87\n" +
            "ING,Cucumber Portion,60,10,0.1,0,,0.7,1.5,1.4,0,Y,0.6,0.48\n" +
            "ING,Cucumber whole,60,10,0.1,0,,0.7,1.5,1.4,0,Y,0.6,0.98\n" +
            "ING,Supersweet Sweetcorn ,90,105,2.3,0.2,,1.5,17,2,0,Y,1.5,1.88\n" +
            "ING,Radish pack 240g,50,12,0.2,0.1,,0.7,1.9,1.9,0,Y,0.9,0.6\n" +
            "ING,Green Peppers losoe,50,16,0.3,0.1,,0.8,2.6,2.4,0,Y,1.6,0.78\n" +
            "ING,Orange Peppers,50,28,0.2,1.7,,1.2,5.3,5.1,0,Y,1.7,0.78\n" +
            "ING,Red Peppers,50,26,0.3,0.1,,1,4.8,4.5,0,Y,1.6,0.78\n" +
            "ING,Yellow Peppers,50,28,0.3,0.1,,1,5,4.5,0,Y,1.6,0.78\n" +
            "ING,Finest Sweet Pointed Peppers,100,33,0.4,0.1,0.4,1,6.4,6.4,0,Y,3.4,1.28\n" +
            "ING,Crispy Potato Slices 360g,108.5,155,6.6,3.2,,1.8,21,0.5,0.5,Y,1.8,1.5\n" +
            "ING,Maris Piper mashed Potato 455g,227.5,80,1.7,1.1,,1.5,14,1.4,0.3,Y,0.9,1\n" +
            "ING,New Potatoes W/ Herband Butter,125,75,2.8,0.9,,1.8,9.6,0.1,0.2,Y,1.7,1\n" +
            "ING,Ready To Roast King Edwards Potatoes,120,125,3.5,1.7,,1.9,21,0.5,0,Y,2.2,1\n" +
            "ING,Potatoes Garlic Butter and Parsley 400g,133.33,115,5.2,3.2,0.1,1.2,15.3,2,0.2,Y,2.3,8\n" +
            "ING,Potato Wedges and Parsley Dressing 570g,114,60,1,0.3,,1,11.5,0.4,0,Y,1.8,1\n" +
            "ING,Cabbage Medley 200g,115,30,0.7,0.1,,2.4,3.5,3.3,0,Y,2.9,1\n" +
            "ING,Peas and Sliced Runner Beans 240g,130,55,1.2,0.2,,4.6,6.4,1.4,0,Y,2,2\n" +
            "ING,Broccoli Florets 350g,100,33,0.9,0.2,,4.4,1.8,1.5,0,Y,2.6,1.12\n" +
            "ING,Butternut Wedges 150g,100,36,0.1,0,,1.1,7.7,4.3,0,Y,1.6,1\n" +
            "ING,Cabbage and Leek 300g,100,28,0.7,0.1,,2.2,3.2,2.9,0,Y,2.4,1.25\n" +
            "ING,Traditional sliced runner beans 185g,100,23,0.4,0.1,,1.6,3.2,2.8,0,Y,2,1.5\n" +
            "ING,Winter Vegetables 250g,100,37,0.8,0.2,,2.6,4.9,4.3,0,Y,3.2,1.5\n" +
            "ING,Vegetable Medley 120g,100,35,0.3,0.1,,2.6,5.4,4.2,0,Y,2.8,1\n" +
            "ING,Sliced Spring Greens 200g,100,33,1,0.1,,3,3.1,2.7,0,Y,2.3,1\n" +
            "ING,Sliced runner beans 150g,100,23,0.4,0.1,,1.6,3.2,2.8,0,Y,2,1\n" +
            "ING,Root Vegetable for Mashing 565g,100,38,0.3,0,,0.9,8,5.4,0,Y,2.6,1\n" +
            "ING,Peeled Baby Sprouts 150g,100,43,1.4,0.3,,3.5,4.1,3.1,0,Y,4.1,1\n" +
            "ING,Carrot Batons 560g,100,37,0.3,0.1,,0.6,7.9,7.4,0,Y,2.4,1\n" +
            "ING,Carrot Cauliflower and Broccoli 305g,100,35,0.6,0.2,,2.5,4.9,4.4,0,Y,2.3,1\n" +
            "ING,Casserole Vegetables 450g,100,39,0.3,0.1,,1.1,8,4.5,0,Y,2.2,1.5\n" +
            "ING,Cauliflower and Broccoli Florets 350g,100,34,0.9,0.2,,3.9,2.5,2.1,0,Y,2.7,1.12\n" +
            "ING,Diced Onion,100,38,0.2,0,,1.3,7.8,5.5,0,Y,1.8,0.5\n" +
            "ING,Hand Shelled Garden Peas 170g,100,70,1.5,0.3,,6.9,6.6,2.3,0,Y,4.7,1.5\n" +
            "ING,Mixed Vegetables 250g,100,33,0.5,0.1,,2.6,4.5,4,0,Y,2.8,1.5\n" +
            "ING,Florette Crispy Salad 200g,100,22,0.3,0.06,,1.5,3.4,0.2,0.02,Y,3,1.24\n" +
            "ING,Florette Mixed Salad 200g,100,20,0.2,0.05,,1.3,3.4,0.3,0.02,Y,3,1.38\n" +
            "ING,Alfresco Salad 248g,54,20,0.3,0,,0.9,3.4,3.3,0,Y,2,1.5\n" +
            "ING,Baby Leaf and Rocket,33,23,0.6,0.1,,1.7,2.7,1.9,0,Y,1.5,1.2\n" +
            "ING,Baby Spinach 240g,45,25,0.8,0.1,,2.8,1.6,1.5,0.3,Y,2.1,1.5\n" +
            "ING,Baby Leaf Herb Salad 90g,30,24,0.3,0.2,,2.6,2.7,0.5,0,Y,2.6,1.5\n" +
            "ING,Wild Rocket Salad 70g,35,15,0.5,0.1,,0.8,1.7,1.7,0,Y,1.4,0.9\n" +
            "ING,Watercress Spinach and Rocket Salad 145g,36.25,25,0.9,0.2,,2.8,1.5,0.8,0,Y,2,1.62\n" +
            "ING,Watercress  75g,44,12,0.1,0,,1.9,0.9,0.1,0.3,Y,1.3,0.85\n" +
            "ING,Sweet and Crunch Salad 285g,57,20,0.3,0,,0.8,3.4,3.2,0,Y,1.9,1\n" +
            "ING,Seasonal Babyleaf salad 83g,100,20,0.5,0.1,,1.8,2,1.3,0.3,Y,1.9,1\n" +
            "ING,Ranch Salad 255g,75,96,6.5,1.3,,1.7,7.6,4.7,0.4,Y,2.1,1\n" +
            "ING,Italian Style Multi leaf salad 133g,45,15,0.5,0.1,,0.8,1.7,1.7,0,Y,1.4,1.3\n" +
            "ING,French Style Salad 143g,75,17,0.6,0.1,,1.4,1.4,1.3,0,Y,1.60,1.6\n" +
            "ING,Four Leaf Salad 85g,38,15,0.5,0.1,,0.8,0.17,1.7,0,Y,1.4,1\n" +
            "ING,Caesar Salad 240g,153,180,15.2,2.8,,4.3,5.8,2,0.9,Y,1.3,2\n" +
            "ING,Beetroot Salad 200g,50,24,0.4,0.1,,1.5,3.7,3.3,0.4,Y,2.1,1.6\n" +
            "ING,Oaktree Estate Coleslaw 500g,100,195,18.2,1.3,,0.3,6.7,6,1,Y,1.5,0.69\n" +
            "ING,Oaktree Estate reduced calorie coleslaw ,100,120,9.3,0.8,,0.8,7.4,6.7,0.6,Y,2.5,0.69\n" +
            "ING,Beetroot Salad 305g,100,65,0.8,0,,0.7,12.8,11.6,0.3,Y,1.6,1.1\n" +
            "ING,Black and Green Olives with feta 100g,50,200,20.1,5.2,,3.4,0.3,0.3,1.9,Y,4.6,1.79\n" +
            "ING,Celery Fruit Nutsalad 300g,100,350,32.5,2.7,,3.2,11.2,11.2,0.7,N,1.9,1.09\n" +
            "ING,Chargrill Chicken and Pepper Pasta Snack 270g,270,205,8.9,0.8,,9.8,20.3,0.7,0,N,3.1,2\n" +
            "ING,Chargrill Chicken Pasta Snack 300g,300,150,5.8,0.8,,6.3,17.9,0.9,0.5,N,2.2,1.4\n" +
            "ING,Chicken Tomato and Basil Pasta 375g,375,190,7.6,1,,6.9,23,2,0.3,N,1.7,2\n" +
            "ING,Chicken and Bacon Sweetcorn Pasta twin,100,225,14.5,1.9,,6.3,16.8,2.5,0.4,N,3.2,2\n" +
            "ING,Chicken and Bacon Pasta 465g,55,225,14.5,1.9,,6.3,16.8,0.7,0.4,N,3.2,1.5\n" +
            "ING,Chicken and Bacon Layered Salad Bowl 380g,190,125,7.5,0.8,,4.8,9.1,1.9,0.5,N,2.7,2\n" +
            "ING,Cheese Layered Salad Bowl 400g,200,170,13.2,3.3,,3.7,8.1,2.9,0.4,N,1.6,2\n" +
            "ING,Cheese Coleslaw 300g,100,255,23.3,4.4,,3.7,6.7,4.6,0.7,N,1.1,0.76\n" +
            "ING,Cheese and Tomato Pasta Pack 300g,300,180,7.1,1.4,,5.5,23,2.8,0.6,N,1.7,1.2\n" +
            "ING,Cheese and Ham Italian Pasta twin 650g,100,195,10.1,1.9,,5.3,19.9,2.8,0.4,N,2.3,2\n" +
            "ING,Chargrill Chicken and Bacon Pasta,375,210,11.8,1.5,,6.7,18.8,1.1,0.5,N,1.3,2\n" +
            "ING,Creamy Coleslaw 180g,100,190,17.8,2.1,,1,5.5,5.2,0.8,N,1.5,0.46\n" +
            "ING,Creamy Potato Salad 300g,100,180,14.2,1.2,,1,11.1,1.1,0.7,N,1.3,0.68\n" +
            "ING,Finest Chargrill Vegetable Couscous 250g,125,155,7.6,1.3,,2.7,19,3.6,0.6,N,2.5,2\n" +
            "ING,Light Choices Creamy Coleslaw ,100,70,4.2,0.4,,1.8,6.4,4.7,0.6,N,1.6,0.65\n" +
            "ING,Italian Pasta 465g,55,140,3.7,0.3,,3.5,22.6,2.8,0.1,N,2.3,1.5\n" +
            "ING,Hot Smoked Salmon Pasta 275,275,155,6.2,0.8,,7.1,16.6,0.8,0.6,N,4.1,2.5\n" +
            "ING,Honey and Mustard Chicken Pasta snack 375,375,230,12.1,1.6,,5.8,24.4,2.3,0.8,N,1.4,2\n" +
            "ING,Tesco Florida Salad 300g,100,130,9.8,0.9,,0.9,9.4,7.7,0.5,N,1.3,1.09\n" +
            "ING,Three Bean and Mint salad 230g,115,130,5.2,0.6,,7.9,12.6,2.4,0.5,N,5,1.99\n" +
            "ING,Roasted Tomato Pasta 200g,100,165,4.3,0.4,,4.9,26,1.5,0.8,N,3.3,2\n" +
            "ING,Potato Salad 275g,137.5,200,16.2,1.4,,1.7,11,1.3,0.6,N,1.3,1.2\n" +
            "ING,Pasta Spinach Pinenut Salad 195g,100,280,17.9,2.2,,6.4,23.1,1.1,0.3,N,1.5,2\n" +
            "ING, Moroccan Couscous,112.5,130,2.5,0.4,,4.3,22.6,11.4,0.2,N,3.7,2\n" +
            "ING,Coleslaw,100,155,14.6,2.6,,1,3.8,2.8,0.5,N,2.7,1\n" +
            "ING,Coleslaw Potato Salad 400g,100,210,17.7,2.6,,1.6,10.4,2.2,0.9,N,1.6,1.29\n" +
            "ING,LiChoices Potato Salad 300g,100,100,5.3,0.6,,1.7,11.2,2.1,0.5,N,1.2,0.68\n" +
            "ING,Potato Salad Creamy Coleslaw Twin pack,100,190,18.2,2.1,,1,5.5,5.2,0.8,N,1.1,1.39\n" +
            "ING,Prawn Layered Salad 200g,200,135,7.2,0.6,,4.2,13.1,3.1,0.3,N,2,1.2\n" +
            "ING,Sausage Past Snack 300g,300,180,5.5,1,,4.9,27.4,1.4,0.5,N,2.8,1.4\n" +
            "ING,Value Potato Salad 250,60,120,8.4,1.1,,0.8,9.9,3.2,0.5,N,1.7,0.28\n" +
            "ING,Value Coleslaw 250g,100,115,9,0.7,,1,6.8,4,0.5,N,1.6,0.2\n" +
            "ING,Tuna and Sweetcorn Pasta Snack 375g,375,195,8.9,1.2,,6.6,21.3,4.2,0.5,N,1.5,2\n" +
            "ING,Spicy Couscous 250g,100,135,2.9,0.2,,3.3,23.2,5.6,0.5,N,1.4,1.09\n" +
            "ING,Spicy Chilli Chicken Pasta 300g,300,155,3.7,0.3,,6.6,23.6,2.2,0.1,N,1.4,1.4\n" +
            "ING,Southern Fried Chicken Pasta 375g,375,205,10.3,1.2,,5.3,22.6,0.7,0.4,N,1.6,2\n" +
            "ING,Slow Roast Tomato Couscous 250g,100,140,2.7,0.2,,4.6,23.9,4.2,0,N,1.6,1.39\n" +
            "ING,Bunched Coriander 120g,120,3,,,,,,,,Y,,\n" +
            "ING,Bunched Curled Parsley 80g,80,4,,,,,,,,Y,,\n" +
            "ING,Fresh Flat Leaf Parsley 30,12,44,0.8,0.1,,3,6.3,0.9,2,Y,3.3,\n" +
            "ING,Fresh Chives 25g,25,12,,,,,,,,,,\n" +
            "ING,Fresh Thai lemon grass,100,23,,,,,,,,,,\n" +
            "ING,Red Chillies 50g Pack,5,60,0.4,0.1,,2.6,10.7,4.5,0.8,Y,2.5,0.54\n" +
            "ING,Aromatic Coriander Blend 75g,5,85,5.1,0.7,,2.6,6.3,5.7,2.8,N,,1.69\n" +
            "ING,Delicate Basil Blend 75g,5,80,4.9,1.2,,2,6.6,5,3.5,N,,1.69\n" +
            "ING,Fragrant Thai Blend 75g,5,65,2.4,0.3,,0.8,9.2,5.4,2.2,N,,1.69\n" +
            "ING,Hot Chilli Blend 70g,5,39,1,0.2,,1.5,5.9,5.3,4.7,N,,1.69\n" +
            "ING,Zesty Ginger Blend 75g,5,50,0.4,0.1,,0.9,10.8,3.7,6,N,,1.69\n" +
            "ING,Intense Garlic Blend 75g,5,125,2.7,0.3,,3.6,20.6,20.6,4.7,N,,1.69\n" +
            "ING,Organic Houmous 200g,50,320,27.2,11,,6.5,12.3,0.8,1.4,N,2.4,0.9\n" +
            "ING,Carmelized Onion Houmous 230g,50,240,19.4,1.9,,4.1,11.9,4.8,1.6,N,4.3,1.15\n" +
            "ING,Light Choice s Sour Cream and Chive Dip 200g,50,130,9,4.1,,4.3,7.4,3,0.2,N,0.1,0.88\n" +
            "ING,cheese and chive dip 200g,25,535,55.1,8.4,,4.3,4.3,2.1,1.2,N,0.1,0.86\n" +
            "ING,Classic multitpack Dips 516g,23.33,465,48.4,6.8,,2.7,4.7,3.2,0.9,N,0.2,2\n" +
            "ING,Cool Salsa dip 200g,50,60,2.7,0.2,,1.1,7.8,5.4,0.4,N,1.2,0.88\n" +
            "ING,Guacamole 200g,50,210,21,8,,1.7,2.6,1.8,1.2,N,3.6,1.84\n" +
            "ING,Houmous,50,310,26.8,2.8,,7.4,9.8,0.9,1.3,N,3.4,0.85\n" +
            "ING,Jalapeno and Red Pepper Houmous 230g,57.5,345,29.8,3.1,,7.4,10.8,1.1,0.6,N,5.3,1.14\n" +
            "ING,Lemon and Coriander houmous 200g,50,340,29,3,,7,12.7,2.8,1.2,N,2.1,1.14\n" +
            "ING,Light Choices Multipack Dips 560g,23.33,180,14.2,3.1,,3.6,8.5,2.7,0,N,0.2,2\n" +
            "ING,Mini Tex Mex Dips 3 X 60g,60,185,,,,1.7,5.5,3.3,0.4,N,1.7,1.35\n" +
            "ING,Morroccan Style Houmous 230g,50,289,25.5,2.6,,6.8,8.1,1,0.9,N,7.3,1.15\n" +
            "ING,Nacho Cheese Dip 200g,50,385,37.8,3.5,,3.1,8.1,4.4,0.8,N,0.5,0.88\n" +
            "ING,Red Pepper pesto houmous 200g,75,275,22.6,2.6,,7.4,9.7,1.5,0,N,5.7,1.14\n" +
            "ING,Onion and Garlic Dip 200g,50,470,48.9,6.6,,1.6,5.5,2.7,1.1,N,0.3,0.86\n" +
            "ING,Olive and sundried tomato houmous,50,280,25,2.7,,7.1,6.8,1,1.2,N,1.2,1.14\n" +
            "ING,Reduced Fat Guacamole Dip 200g,50,140,11.4,2.6,,2.7,5.7,2.6,0.6,N,3,1.84\n" +
            "ING,Reduced Fat Houmous Dip 200g,50,241,16.8,2,,9.2,4.6,0.2,1,N,3.6,0.88\n" +
            "ING,Tzatzik Dip 200g,50,145,12,4.5,,4,5.1,3.1,0.5,N,0.2,0.88\n" +
            "ING,Tex Mex Multipack Dips 428g,25,280,27.3,6.6,,2.7,5.2,2.7,0.8,N,1.3,2\n" +
            "ING,Taramasalata 200g,50,515,52.5,3.5,,2.5,8,1.6,1.2,N,0.3,0.78\n" +
            "ING,Sour Cream and chive dip 200g,50,280,27.4,9,,2.3,6,2.6,0.6,N,0.2,0.86\n" +
            "ING,smoked salmontaramasalata 200g,50,480,47.7,3.3,,4.8,7,1.2,1.3,N,0.2,1.14\n" +
            "ING,Chinese Stir Fry Sauce 175g,43.8,120,5.6,0.4,,2.1,14.2,10.6,3.6,N,0.8,0.95\n" +
            "ING,Fresh Noodles 410g,100,140,1.9,0.2,,4.9,25.3,0.4,0,N,2,1\n" +
            "ING,Rice Noodles 375g,100,160,5.6,0.5,,2,24.8,1.1,0,N,1,1\n" +
            "ING,Sweet Chilli Sitr Fry Sauce 175g,37.5,11,0.2,0,,0.6,26.5,21.4,0.6,N,0.6,1\n" +
            "ING,Beansprout 335g,111,31,0.5,0.1,,2.9,3.7,2.1,0,Y,1.5,0.48\n" +
            "ING,Chines Stir Fry Veg 360g,100,30,0.4,0.1,,2.1,4.6,3.4,0,Y,2.1,1.5\n" +
            "ING,Family Pack Stir Fry 600g,100,35,0.5,0.1,,2.3,5.2,3.5,0,Y,2.1,1.5\n" +
            "ING,Mixed Pepper Stir Fry 320g,190,23,0.3,0.1,,1.8,3.3,3,0,Y,1.3,1\n" +
            "ING,Vegetable and Beansprout Stir Fry 380g,100,34,0.5,0.1,,2,5.4,4.1,0,Y,2.2,1\n" +
            "ING,Mushroom Stir Fry 355g,100,31,0.5,0.1,,2.6,4,3,0,Y,1.9,1\n" +
            "ING,Bernard Matthews Premium Chicken 200g,20,115,3,0.9,,19,3,1.8,1.8,N,0.45,2.59\n" +
            "ING,Bernard Matthews Premium Turket Breast 100g,20,109,1.1,0.4,,23.8,0.9,0.7,1.5,N,0.6,1.49\n" +
            "ING,Bernard Matthews Wafer Thin Turkey Ham 200g,20,103,4.1,1.2,,13.6,3,1,2.5,N,5,1.49\n" +
            "ING,Organic Moroc Chickpea Pate 115g,10,210,37.7,2.2,,6.8,16.3,7.6,2.5,N,4.9,1.31\n" +
            "ING,Aberdeen Angus Beef Topside 915g,30.3,160,3.8,1.7,,31.3,0.2,0.2,1.3,N,0.3,2.89\n" +
            "ING,Proscuitto Cotto Al Rosmarino 120g,24,130,5,2.6,,20,0.5,0.5,1.5,N,0,2.69\n" +
            "ING,Thick Carve Honey Roast Ham 120g,40,151,5.9,2,,22.1,2.3,2.3,1.7,N,0.5,2.35\n" +
            "ING,Thick Carve Roast Ham 120g,40,163,8.5,3.3,,21.4,0.3,0.2,1.9,N,0.5,2.46\n" +
            "ING,Wafer Thin Applewood Smoked Ham 115g,14.3,120,2.7,1.1,,22.8,1.2,1.2,2.2,N,0.5,2.46\n" +
            "ING,Yorkshire Cure Roast Ham 120g,35,155,6,2,,24.4,0.1,0,2.2,N,0.5,2.68\n" +
            "ING,Fridge Raiders 65g ,65,192,10.15,3.85,,21.3,3.54,1.85,1.23,N,0.62,0.97\n" +
            "ING,Mattessons Reduced Fat Smoked Pork Sausages 227g,100,225,19,7,,14,7,3,2.3,N,0.9,1.38\n" +
            "ING,Mattessons Smoked Pork Sausage 227g,100,320,30,11,,13,0.5,0.3,2.3,N,0.9,1.38\n" +
            "ING,Morliny Kabanos 150g,100,366,30.4,12.4,,23,0,0,3.8,N,0,1.5\n" +
            "ING,Peperami 5 Pack 5 x 25g,25,126,11,4.5,,6.1,0.6,0,0.9,N,0,1.02\n" +
            "ING,Quorn Deli Roast Chicken Style 100g,25,30,0.6,0.2,,4.3,1.8,0.8,0.3,N,0.8,1.29\n" +
            "ING,Quorn Deli Smokey Ham Style 100g,25,30,0.7,0.3,,4.2,1.9,0.4,0.3,N,1.1,1.29\n" +
            "ING,Quorn Pepper Beef Style Slices 100g,25,27,0.5,0.3,,3.6,1.9,0.4,0.3,N,1,1.43\n" +
            "ING,Honey Roast Ham 126g,25,130,3.8,1.4,,21.8,1.9,1.9,2.5,N,0,1.69\n" +
            "ING,Mini Slices Crumbed ham 126g,25,110,2.2,0.8,,19.7,2,0.7,1.9,N,0,1.69\n" +
            "ING,12 Value Meatballs 216g,84,236,17.2,8.2,,13.4,6.3,4,1.3,N,2.6,1\n" +
            "ING,20 Beef meatballs 360g,125,285,23.7,10.8,,15.3,2,1.1,0.7,N,1.8,2\n" +
            "ING,Amercan Style Pepperoni 110g,27.5,390,36.6,15,,13.8,0.5,0.5,3.8,N,0.3,1.38\n" +
            "ING,Ardennes Pate 175g,40,305,25.5,10.4,,13,4.9,3.4,1.6,N,0.8,0.66\n" +
            "ING,Adrennes Pate with bacon 170g,50,275,23,9.1,,13.5,3.5,1.7,2.2,N,1.3,1.49\n" +
            "ING,Roast Beef 100g,26,127,3.9,1.8,,22.4,0.6,0.2,2.5,N,0,1.69\n" +
            "ING,Wafer thin turkey ham 280g,10,110,3.5,1.1,,15.1,4.2,1.6,2.4,N,0,1.65\n" +
            "ING,Brussels Pate 175g,40,355,33.1,13.8,,9.6,4.5,3.1,1.6,N,0.7,0.66\n" +
            "ING,Brussels Pate with Garlic 170g,40,363,33.8,12.3,,8.7,6,2.4,2.1,N,0,1.18\n" +
            "ING,Chorizo Selection Pack 100g,25,395,33.4,12.5,,22.7,1,0.7,3.3,N,0.6,1.69\n" +
            "ING,Cooked Beef Sandwich Slices 10 125g,12.5,115,2.6,0.9,,19.1,2.9,0.8,0.9,N,0,1\n" +
            "ING,Cooked Chicken Sandwich Slices 10 125g,12.5,100,1.5,0.5,,19.7,1.5,1.2,1.5,N,0.5,1\n" +
            "ING,Cooked Ham Sandwich Slices 10 125g,12.5,103,3.2,1.1,,18.3,0.2,0.2,2.4,N,0,1\n" +
            "ING,Cooked Turkey Sandwich Slices 10 125g,12.5,110,2.3,0.8,,21.2,0.8,0.5,2,N,0,1\n" +
            "ING,Ardenne Pate 170g,40,315,28,10.2,,11.3,4.5,2.2,2.1,N,1.8,2.09\n" +
            "ING,Antipasto 120g,25,395,27.3,9.2,,36,0.5,0.5,4.8,N,0,3.39\n" +
            "ING,18 month Parma Ham 70g,12.5,255,15,5.2,,29.5,0.5,0.5,5.2,N,0,2.49\n" +
            "ING, 14 month genuine Parma Ham 85g,12.5,235,28,4.5,,28,0.5,0.5,4.9,N,0,1.99\n" +
            "ING,Farmhouse Pate with wild mushrooms 170g,50,286,24.5,9.8,,14.1,2.2,0.9,1.9,N,0.5,1.18\n" +
            "ING,Duck and orange pate slice 170g,40,372,34,12.5,,7.5,8.9,4.6,1.7,N,0.6,1.18\n" +
            "ING,Duck and orange pate 175g,40,355,32.5,12.5,,8.3,7,5.1,2,N,0,0.79\n" +
            "ING,Danish Salami 125g,20,350,29,11.2,,20.1,1.5,1.5,3.8,N,0,0.78\n" +
            "ING,Brussles Pate 170g,40,360,33.7,12.5,,8.5,5.2,2.9,2,N,0.8,1.97\n" +
            "ING, Butter Roast Turkey 120g,30,110,1.3,0.6,,24.3,0.3,0.3,1.8,N,0.5,2.46\n" +
            "ING,Chicken and Stuffing 120g,30,136,3.5,1.5,,21.6,4.5,1.7,1.4,N,0.5,2.46\n" +
            "ING, Chorizo Ring 225g,50,455,39.2,15,,24.3,1,0.4,3.5,N,0.7,2.35\n" +
            "ING, Crumbled Wiltshire Cured Ham 120g,30,125,3.1,1.2,,23.9,0.5,0.5,2.3,N,0.4,2.46\n" +
            "ING, Duck Pate with Port 170g,50,295,24.3,9.5,,10.3,8.2,4.5,0.18,N,0.4,1.98\n" +
            "ING,Five Pepper Pork Loin 110g,13.8,165,7,2.80,,23.9,0.9,0.8,1.8,N,0.4,2.68\n" +
            "ING,Garlic Sausage 150g,12.5,235,18,6,,15.8,2,1,1.5,N,0,0.54\n" +
            "ING,Yorkshire Crumbed Ham 120g,30,155,5.1,1.8,,24.8,1.7,0.9,1.6,N,0.5,2.68\n" +
            "ING, Wiltshire Cured Ham 120g,30,125,3,1.1,,24.2,0,0,2.3,N,0.3,2.46\n" +
            "ING, Wholegrain Mustard Wiltshire Cured Ham 120g,30,125,3.1,1.1,,23.5,0.7,0.3,2.3,N,0.5,2.68\n" +
            "ING, Wafer Thing Honroast Wiltshire Cured ham 115g,14.3,123,2.9,1,,24.3,0,0,2.4,N,0.3,2.49\n" +
            "ING, Smoked Pancetta 100g,25,375,33,11.4,,18.5,1,1,4,N,0,1.89\n" +
            "ING, Salami miliano 70g,25,360,27.3,9.2,,27,0.5,0.5,2.8,N,0,1.79\n" +
            "ING,Honey Roast Wiltshire Cured Ham 120g,30,130,2.9,1,,24.8,0,0,2.3,N,0.3,2.46\n" +
            "ING, Italian Style meatballs 315g,125,225,16.1,7.4,,15.4,3.7,0.4,1,N,2,1.97\n" +
            "ING, Roast Beef Topside 100g,33.3,158,3.8,1.8,,30.5,0.4,0.2,1.5,N,0.4,2.46\n" +
            "ING, Roast Chicken 120g,30,120,1.8,0.8,,24.6,0.6,0.4,1.4,N,0.5,2.46\n" +
            "ING,German Peppered Salami 140g,25,335,26,10.7,,23.8,1,1,4,N,0.7,1.39\n" +
            "ING,German Salami 170g,25,310,25,10.3,,20,1,1,3.8,N,0.3,1.39\n" +
            "ING,German Salami selection 120g,30,325,25.5,10.5,,21.9,1,1,4,N,0.5,1.63\n" +
            "ING,German Sausage selection 110g,27.5,285,25,9.9,,13.1,1,0,2.3,N,0,1.38\n" +
            "ING,German smoked ham 150g,37.5,130,4,1.4,,21.5,1,1,2,N,0,1.89\n" +
            "ING,Haslet 125g,15.6,220,14.6,5.9,,13.2,8.3,1.1,2.4,N,0,0.64\n" +
            "ING,Herb Pork Sausage 125g,12.5,275,20.8,6.9,,14.4,7.1,0,1.6,N,0,1\n" +
            "ING,Ingredients Cooking Chorizo Sausages 250g,50,455,39.8,15,,19.7,4.5,3.3,4.2,N,1.6,2.5\n" +
            "ING,Ingredients Smoked Pancetta Cubes 150g,37.5,360,31.5,4.7,,17.7,0.5,0.5,3,N,0,1.55\n" +
            "ING,LiChoices Wafer Thin Roast Ham 100g,14.2,110,2.2,0.8,,21.8,0,0,1.8,N,0,1.79\n" +
            "ING,LiChoices Roast Turkey 85g,20,115,1.3,0.5,,24.1,0.8,0.1,1.2,N,0.6,1.79\n" +
            "ING,LiChoices Roast Chicken 85g,21.25,120,2.6,1,,23.2,0.2,0.2,1.2,N,0.5,1.79\n" +
            "ING,LiChoices Honey Roast Ham 100g,20,115,2.2,0.8,,21.5,2.3,2.2,1.8,N,0,1.79\n" +
            "ING,LiChoices Crumbed Ham 100g,20,110,2.1,0.8,,21.5,0.2,0,1.8,N,0,1.79\n" +
            "ING,LiChoices cooked ham 100g,20,100,1.6,0.9,,20.8,0.4,0.2,1.9,N,0,1.79\n" +
            "ING,Kids Billy Bear Sausage 100g,20,189,12.6,7.1,,12.2,6.6,1.4,1.7,N,8,1.06\n" +
            "ING,Mickey Mouse Sausage 100g,20,166,9.6,3.3,,12.1,7.9,0,1.8,N,0.7,1.12\n" +
            "ING,Corned Beef 150g,37.5,200,10,4.7,,26.4,0.7,0.7,1.9,N,0,1.69\n" +
            "ING,Sweetcure Pork Loin 126g,15,150,5.8,2.3,,20.3,2.5,2.2,2.3,N,0.6,1.69\n" +
            "ING,Mustard Ham,25,145,5.4,1.9,,20,3.2,1.7,2.1,N,0.5,1.69\n" +
            "ING,Roast Pork 126g,25.2,140,4.9,1.8,,23.1,0.4,0.4,1.7,N,0.5,1.69\n" +
            "ING,Roast Turkey 126g ,25,125,4.2,1.4,,21,0.9,0.9,2.1,N,0.5,1.69\n" +
            "ING,Smoked Ham 126g,25,130,4.6,1.7,,21,0.6,0.6,2.8,N,0,1.69\n" +
            "ING,Pastrami,14,131,3.1,1.2,,25.4,0.3,0.3,3.1,N,0.3,1.69\n" +
            "ING,Pork and Apricot Pate 170g,40,406,37.5,13.8,,8.8,8.4,4.4,1.8,N,0,1.68\n" +
            "ING,Pork and Sweet Pate 170g,40,354,30.3,10.7,,11.6,8.8,5.5,1.8,N,1.1,1.68\n" +
            "ING,Pork Lunch Tongue 126g,25,180,10.4,4.3,,20.4,0.8,0.8,2.6,N,0,1.69\n" +
            "ING,Strippers Smoked Crispy Bacon,25,475,26.8,10.8,,57.4,0.7,0,6.8,N,0,1.52\n" +
            "ING,Swedish Style Meatballs 350g,86,245,17.7,6.9,,14.3,6.5,3.8,1.6,N,2,1.99\n" +
            "ING,Value Chicken Roll 400g,13,210,15.3,4.7,,12.1,4.8,0.6,1.3,N,0.5,0.94\n" +
            "ING,Value Chopped Pork 250g,15.6,280,23.6,8.1,,14.5,1.4,1.4,1.7,N,0,1.15\n" +
            "ING,Tuna Pate 115g,40,249,19.6,1.9,,17.1,1,0.1,0.9,N,2.5,0.99\n" +
            "ING,Value Liver and Bacon Spreading Pate 150g,20,285,24,9,,13.5,3.2,1,1.7,N,2.1,0.56\n" +
            "ING,Value Liver Sausage 200g,12.5,200,12.8,4.2,,16.1,4.5,1.6,1.7,N,0,1.01\n" +
            "ING,Value Polony Slicing Sausage 150g,100,250,19.8,7.8,,11.8,6,2.4,2.4,N,1.2,0.48\n" +
            "ING,Value Pork and Egg Loaf 250g,20.8,295,24.1,9,,13.2,5.7,0,2.5,N,0,1.08\n" +
            "ING,Value Pork Luncheon Meat 250g,13.9,240,19.2,7.2,,13.5,3.3,0.8,3,N,0,0.61\n" +
            "ING,Wafer Thin Beef 200g,8,115,2.6,0.9,,19.1,2.9,0.2,1.6,N,0,1.65\n" +
            "ING,Wafer Thin Honey Roast Ham 200g,8,125,3.8,1.3,,16.8,5,3.5,2.8,N,0,1.65\n" +
            "ING,Wafer Thin Roast Chicken 200g,8,131,5.1,1.5,,18.7,2.6,0.3,2,N,0,1.65\n" +
            "ING,Wafer Thin Roast Turkey 70g,8,105,2,0.6,,20,1.5,0.4,1.4,N,0,1.65\n" +
            "ING,Tulip Breaded Ham 320g,12.5,111,3.4,1.4,,17.9,2.1,0.5,2.5,N,0,1.99\n" +
            "ING,Danepak maple Cure Back Bacon 220g,32,242,19.8,6.6,,15.5,0.4,0.4,2,N,0,3\n" +
            "ING,Danepak Smoked Thick Cut Bacon 220g,32,242,19.8,6.6,,15.9,0,0,3.3,N,0,3\n" +
            "ING,Danepak Unsomked Thick cut bacon 220g,32,242,19.8,6.6,,15.9,0,0,3.3,N,0,3\n" +
            "ING,Discounter Applewood Smoked Bacon 250g,32,320,26.1,0.87,,20.2,0,0,4,N,0,2.19\n" +
            "ING,Discounter Lardons 180g,32,295,26.9,8.9,,13.2,0,0,3.3,N,0,1\n" +
            "ING,Discounter Sweet Cure Back Bacon 250g,32,350,29.7,9.9,,19.5,1.1,1.1,2.5,N,0,2.19\n" +
            "ING,Smoked Gammon Steaks 450g,170,135,6.8,2.2,,18,0.1,0.1,2,N,0,4\n" +
            "ING,Smoked Gammon Joint 1.2kg,150,165,10.4,4,,16.8,0.2,0.2,2.8,N,0,5\n" +
            "ING,Suffolk Crown Sweetcure Back Bacon 200g,32,241,13.5,0,,27.7,2.2,0,2,N,0,1.99\n" +
            "ING,Richard Woodall Air Dried Pancetta 180g,32,58,4.7,1.8,,3.3,0.1,0.1,0.7,N,0.1,2.78\n" +
            "ING,Key Country Foods Unsmoked Thick Gammon Steaks ,170,138,7.3,2.4,,18.1,0,0,2.3,N,0,2\n" +
            "ING,Key Country Bacon unsmoked drycure back bacon,30,311,21,7.9,,30.4,0.1,0.1,3.6,N,0,2\n" +
            "ING,Discounter unsmoked gammon joint 1.1 kg,100,197,12.2,4.5,,21.9,0,0,2.9,N,0,5.49\n" +
            "ING,Discounter unsmoked twin back,30,320,25.8,8.7,,21.1,0,0,4,N,0,2.79\n" +
            "ING,Finest Wiltshire smoked gammon joint,150,105,2.7,0.8,,19.4,0.3,0.1,2.7,N,0.5,12.58\n" +
            "ING,Finest Wiltshire unsmoked gammon joint,150,95,0.8,0.3,,20.6,0.5,0.3,3.2,N,0.5,6.99\n" +
            "ING,Key Country bacon rindless back bacon,32,239,17,8.1,,21.5,0,0,3.60,N,0,2\n" +
            "ING,Unsmoked Healthy Eating Bacon Rasher 200g,32,120,4,1.8,,20.6,0,0,2,N,0,2\n" +
            "ING,Unsmoked organic rindless back bacon 250g,32,225,17.8,5.9,,16.2,0,0,3,N,0,2.45\n" +
            "ING,Value Cooking bacon 500g,125,280,22.9,8.9,,16.6,1.4,1.1,2.8,N,0,1.14\n" +
            "ING,Black Farmer 6 Premium Pork Sausages 400g,65,241,19.7,8.6,,14.8,1,0.5,1.3,N,0,2.39\n" +
            "ING,Broad Oak Farm 8 Large Pork Sausages 454g,57,176,14.6,5.4,,6.8,4.4,0.2,1,N,0.2,2.49\n" +
            "ING,Bury Black Pudding 4 Slices 230g,57,127,2.5,0,,9.5,16.6,0,0,N,0,1\n" +
            "ING,Butcher's Choice 12 Pork Chipolata 340g,25,260,19.4,7.6,,11.8,9.2,3.5,1.2,N,1.6,1.87\n" +
            "ING,Butcher's Choice 8 Beef Sausages 454g,54,235,15,6.5,,17.6,6.4,1.6,1.3,N,1.8,1.99\n" +
            "ING,Butcher's Choice 8 Chicken Sausages 454g,54,220,14.6,3.9,,15.5,5.8,1.4,1.3,N,1.7,1.99\n" +
            "ING,Butcher's Choice 8 Cumberland Sausages 454g,48,265,20,7.5,,12.2,9,3.3,1.2,N,1.7,1.97\n" +
            "ING,Butcher's Choice 8 H/Fat Pork Sausages 454g,54,185,8.8,3.6,,15.1,10.3,3.7,1.2,N,2,1.87\n" +
            "ING,Butcher's Choice 8 Lincolnshire Sausages 454g,43,265,20,7.5,,12.2,9,3.3,1.2,N,1.7,1.97\n" +
            "ING,Butcher's Choice 8 Pork Sausages 454g,43,260,19.4,7.6,,11.8,9.2,3.5,1.2,N,1.6,1.97\n" +
            "ING,Debbie and Andrew 6 Harrogate 97% Sausages 400g,32,168,13.6,4.7,,11.3,0.1,0,0.7,N,2.6,2.39\n" +
            "ING,Halls Haggis 500g,250,240,13.7,0,,13.6,15.3,0,0,N,0,2\n" +
            "ING,Herta 10 Frankfurters 350g,35,98,8.8,3.4,,4.2,0.7,0.7,0.6,N,0.5,1.79\n" +
            "ING,Herta 10 Frankfurters Chicken 350g,35,71,5.6,1.7,,4.6,0.7,0.7,0.7,N,0,1.79\n" +
            "ING,Herta 4 Jumbo Frankfurters 360g,90,253,22.5,8.8,,10.8,1.8,1.8,1.6,N,1.3,2.25\n" +
            "ING,Light Choices 8 Cumberland Sausages 454g,50,131,3.2,1.2,,15.3,8.2,1.3,1.3,N,1.9,1.87\n" +
            "ING,Oak Crown 6 Pork and Apple Sausages 454g,76,270,20,7.5,,10.4,11.9,7.1,1.3,N,2.5,1.99\n" +
            "ING,Oak Crown 6 Pork Sausages 454g,76,250,17,6.4,,12.9,10.7,3.2,1.2,N,2.1,1.99\n" +
            "ING,Oak Crown 6 Irish Sausages 454g,57,270,19.9,7,,12.6,10.1,4.2,1.2,N,1.5,0.95\n" +
            "ING,Red Dog Chilli Frankfurters 350g,35,240,20,0,,14,1,0,0,N,0,2\n" +
            "ING,Richmond 12 Skinless Pork Sausages 319g,26,273,21.6,11.2,,8.5,11.2,0.3,1.6,N,1.3,1.65\n" +
            "ING,Richmond 12 Thick Pork Sausages 681g,57,295,22.2,11.6,,9.9,13.7,0.7,1.9,N,2.5,2.89\n" +
            "ING,Richmond 12 Think Pork Sausages 340g,27,254,15.9,8.1,,11.7,16,0.9,2.2,N,2.6,1.65\n" +
            "ING,Black Pudding Slices 240g,40,295,18.6,6.4,,12.6,18.4,0.5,1,N,1.3,0.96\n" +
            "ING,Cumberland Chipolata 375g,32,285,22.8,9.4,,14.2,4.7,3.1,1.3,N,1.5,2.59\n" +
            "ING,Lincolnshire Chipoloat 375,32,255,19,7.6,,14.9,5.2,3.7,1.3,N,1.1,2.59\n" +
            "ING,Traditional Pork Chipoloata 375g,32,270,20.3,8.2,,14.5,4.1,1.5,1.3,N,1,2.59\n" +
            "ING, Pork Cocktail Sausages 292g,48,269,20.3,8.2,,14.5,4.1,1.5,1.3,N,1,2.59\n" +
            "ING,Pork and Herb Sausages 454,76,255,20.3,8.6,,14.6,3.5,1.7,1.2,N,1.3,2.59\n" +
            "ING,Pork and Leek Sausages 454,76,255,20,7.9,,13.5,4.9,3.6,1.1,N,1.4,2.59\n" +
            "ING,Gourmet Peppered Steak Burgers 284g,109,215,13.8,6.3,,17.1,4.6,0.6,0.4,N,0.5,1.99\n" +
            "ING,Quarter pounder beef burger,85,265,21.5,9.9,,15,2.9,1.2,0.7,N,0.9,2.12\n" +
            "ING,Quarter pounder chargrilled burger,86,240,17.6,7.7,,15.9,4,1.3,0.6,N,0.5,2.47\n" +
            "ING,Quarter pound bugers with onion,87,265,21.5,9.8,,14.5,3,1.8,0.4,N,1.7,2.47\n" +
            "ING,2 chicken burgers in a bun ,225,250,11.4,1.7,,9.5,26.6,2.8,0.9,N,1.5,2.47\n" +
            "ING,Finest gourment lamb burgers 284,109,220,16.9,7.8,,15.8,1.1,1.1,0.8,N,2.1,1.99\n" +
            "ING,2 Peppered Beef Grill Steaks 340g,133,300,24.4,11.1,,13.9,6.3,1.7,0.6,N,0.5,2.12\n" +
            "ING,4 Scotch Premium Beef Burgers 454,88,240,18,8.4,,16.3,3.3,0.7,0.7,N,0.9,2.99\n" +
            "ING,Barbeque park rib pack 450g,140,230,12.9,4.9,,21.5,6.6,6.6,0.6,N,0.1,3\n" +
            "ING,Cheese burgers in a bun 525g,238,245,12,5.5,,11.4,22.4,7,0.7,N,2.5,2.47\n" +
            "ING,Chinese Rib Rack 450g,150,220,12.8,5.3,,20.8,5.3,4.8,0.7,N,0.1,3\n" +
            "ING,Finest Beef Burgers 454g,87,195,12.4,5.5,,17.2,3.3,0.8,0.9,N,0.9,2.99\n" +
            "ING,Finest caramelised onion burger 454g,96,250,17.5,7.8,,16.1,6.2,2.1,1,N,0.6,3\n" +
            "ING,Finest gourmet aberdeen angus burgers 284,120,210,13.8,6.3,,19.3,1.5,1.2,0.7,N,0.7,1.99\n" +
            "ING,Minted Lamb Kebabs 240g,40,280,21.7,11.4,,15.7,4.7,2.1,0.9,N,1.3,2\n" +
            "ING,Minted Loin Lamb chops 280g,28,215,14.7,7.2,,17.7,2.8,2.4,0.8,N,0.5,3\n" +
            "ING,Organic 4 quarter pound beef burgers,82,245,19.2,9.4,,16,1.3,0.5,1,N,2.3,3\n" +
            "ING,Bernard Matthews Breaded Turkey Escalopes 390g,93,289,17.7,4.7,,14.8,17.5,1.2,1.2,N,2.3,1.99\n" +
            "ING,Bernard Matthews Lemon/Pepper Turkey Escalopes 260g,90,304,18.8,2.8,,13.4,20.2,1.4,1.28,N,2.5,2\n" +
            "ING,Market Value Chicken Legs ,333,230,17.7,6.3,,17.6,0,0,0.3,N,0,1.99\n" +
            "ING,Market Value Diced Turkey Thigh 500g,125,165,9.1,2.1,,19.7,0,0,0.3,N,0.5,2.4\n" +
            "ING,Chicken Breast Quarter,240,197,13.1,3.8,,19.7,0,0,0.2,N,0,1.63\n" +
            "ING,2 Breaded Chicken Breast Fillets 350g,175,205,7.4,0.7,,18.3,15.3,0.5,0.5,N,0.5,3\n" +
            "ING,2 Cheese and Ham Chicken Kievs 260g,130,255,17.2,4.7,,12.3,12.3,0.6,0.8,N,1.3,1.98\n" +
            "ING,2 Chicken breast garlic kievs 295g,147.5,260,15.5,4.2,,15.6,13.7,0.6,0.2,N,0.6,3\n" +
            "ING,2 Chicken breast cheese / ham / mushroos 360g,162.5,121,5.3,3.1,,16.9,1.5,1,0.5,N,0.1,3\n" +
            "ING,2 Chicken breast fillets with barbeque sauce 335g,147,112,1.7,0.6,,16.6,7.6,6.6,0.7,N,0.4,3\n" +
            "ING,2 Chicken breast peppers onion and tomato butter,118,126,4.8,1.6,,19.2,1.6,1.6,0.2,N,0.1,3\n" +
            "ING,2 Chicken breast wrapped in bacon with cheese,136,158,7.4,2.5,,22.4,0.4,0.3,0.8,N,0.1,2.99\n" +
            "ING,2 Chicken breast / garlic mushroom 275g,125,119,4.7,1.6,,19,0.2,0.1,0.3,N,0.1,2.58\n" +
            "ING,2 Chicken cheese and ham encroutes 440g,220,244,12.3,6.5,,14.9,18.2,1.9,0.6,N,1.4,3\n" +
            "ING,2 chicken min roast with gravy 424g,217,123,5.9,2,,16.5,1,1,0.6,N,0.1,3\n" +
            "ING,2 creamy garlic chicken kieve 260g,130,235,15.9,3.5,,11.5,11.5,0.6,0.7,N,1.4,1.76\n" +
            "ING,2 Creamy peppercorn chicken kievs 260g,130,240,15.8,3.5,,11.5,12,0.6,0.7,N,1.4,1.98\n" +
            "ING,2 Garlic Chicken Kievs 260g,130,315,25.2,6.6,,10.9,11.1,0.5,0.8,N,1.4,1.76\n" +
            "ING,2 Pack roast chicken boneless breast fillets 245g,125,160,6.7,2.3,,23.9,0.2,0.1,0.7,N,0.1,3\n" +
            "ING,2 Pack Roast Chicken legs 325g,162,210,12.5,3.9,,24,0.3,0.3,0.8,N,0,2.1\n" +
            "ING,2 reduced fat garlic chicken kievs 260g,130,230,14.3,4.5,,13.7,11.5,1.2,0.7,N,0.7,1.76\n" +
            "ING,2 Tomato/ Mozzarella chicken breast kievs 295g,130,200,8.7,1.4,,16.8,13.1,1.1,0.5,N,0.6,3\n" +
            "ING,4 breaded chicke breast fillet 600g,150,205,7.7,0.8,,18.8,14.9,0.5,0.5,N,0.5,5\n" +
            "ING,4 pack roast chicken boneless breast fillets,121,160,6.7,2.3,,23.9,0.2,0.1,0.7,N,0.1,5.69\n" +
            "ING,barbeques wings bucket 554g,140,218,13.6,3.7,,21.2,2.6,2,5.2,N,0.4,3\n" +
            "ING,breaded chicken escalopes 255g,125,280,17.6,2.4,,9.5,20.4,0.7,0.6,N,1.9,2\n" +
            "ING,breaded chicken goujons 275g,86.2,275,16.8,2.8,,11,19.7,0.6,0.6,N,1.5,2\n" +
            "ING,breaded chicken mini fillet 305g,138,205,8.3,0.9,,17.7,14.5,0.4,0.8,N,0.7,3\n" +
            "ING,breaded chicken pops 205g,50,270,16,2.5,,11.3,19.4,0.7,0.9,N,1.5,2\n" +
            "ING,breaded chicken steaks 505g,126.3,265,14.4,2.5,,13,19.9,0.8,0.3,N,1.4,2\n" +
            "ING,breaded southern fried chicken wings 500g,159,245,15.3,3.4,,16.4,10,0.6,1,N,0.5,2.25\n" +
            "ING,bumper pack chicken drum sticks and thighs,120,230,17.7,6.3,,17.6,0,0,0.3,N,0,3\n" +
            "ING,chicken drumsticks 810g,190,230,17.7,6.3,,17.6,0,0,0.3,N,0,3\n" +
            "ING,chicken wings 1kg,255,210,15.3,4.3,,17.9,0,0,0.3,N,0,2.5\n" +
            "ING,Chillig mango mini chicken fillets 200g,100,130,0.7,0.2,,27.1,3.7,3.7,1.3,N,1,2.75\n" +
            "ING,Healthy living turkey breast,120,100,1.2,0.6,,22.3,0,0,0.3,N,0,5\n" +
            "ING,Tulip Cheese and bacon grills 312g,80,241,15.8,5.1,,12.5,12.3,1.3,2.6,N,0.8,1.69\n" +
            "ING,Tulip Spam Fritters 320g,150,247,12.3,4.3,,11.9,22.4,1.5,1.5,N,0.9,1.76\n" +
            "ING,All Day Breafast Sandwich,188,240,8.5,2.7,,11,29,2.4,1,N,2.6,1.5\n" +
            "ING,Bacon Lettuce Tomato Malted Brownbread Sandwich,221,235,10.9,2.5,,13.6,20.7,3.8,1.5,N,2.5,1.6\n" +
            "ING,Cajun Chicken Wrap,185,225,9,2.3,,9.8,25.1,3,1,N,1.9,2\n" +
            "ING,Cheese and Coleslaw sandwich,167,285,17.2,5.6,,10.4,21.2,1.2,0.9,N,3,1.5\n" +
            "ING,Cheese and onion brown bread sandwich,181,305,1.2,6.6,,11.9,23,1.9,1,N,3,1\n" +
            "ING,Cheese and pickle sandwich,139,255,9.4,5.7,,12,30.1,4,1.1,N,2.8,1\n" +
            "ING,Triple cheese sandwich,281.25,240,12.2,4.8,,10.2,22,2.6,0.9,N,2.7,1.6\n" +
            "ING,Chicken and Bacon Club Sandwich,256.82,220,8.2,2.4,,12.1,24.3,2.7,0.9,N,2.3,2.2\n" +
            "ING,Chicken and Bacon Sandwich,164.06,281,14.9,2,,13.2,23.6,2,0.9,N,2,1.3\n" +
            "ING,Chicken and Bacon Sub,201.92,260,10.6,1.7,,14.2,26.6,3.1,1.1,N,1.8,2\n" +
            "ING,Chicken and Sweetcorn Sandwich,160.86,230,9.6,5.7,,11.4,23.7,1.6,0.5,N,2.2,1\n" +
            "ING, Chicken Bacon and Lettuce Brown Bread Sandwich,208.33,240,10.6,3.1,,13.9,21.9,2.4,1,N,1.7,1.8\n" +
            "ING, Chicken Ceasar Sandwich,186.36,220,10.1,1,,14,17.8,1.4,0.5,N,1.8,1.5\n" +
            "ING,Chicken Ceasar Wrap,214.54,240,11.3,2.5,,11.6,23,3.3,0.7,N,1.2,2\n" +
            "ING, Chicken Fajita Wrap,318,185,4.9,2.1,,10.2,24.7,3.4,0.6,N,1.4,2\n" +
            "ING,Chicken Salad Sandwich,217,180,5.8,0.6,,12.9,18.7,1.7,0.5,N,2.8,1.8\n" +
            "ING, Chicken Sushi ,147,165,2.4,0.4,,5.2,30,5.1,0.6,N,1,2\n" +
            "ING, Chicken Triple Sandwich,295,195,7.7,2,,13.7,17.3,1.7,1,N,2.3,2.2\n" +
            "ING,Corned Beef and Picle Sandwich,153,210,4.8,2.2,,13.5,28.2,4.8,1.6,N,1.6,1.5\n" +
            "ING, Egg and crest sandwich,170,230,11.1,1.3,,10,22.6,1,0.9,N,2.6,1\n" +
            "ING, Egg and crest triple sandwich,255,200,8.8,1.4,,10.7,19.2,0.8,0.9,N,4.7,1.5\n" +
            "ING, Egg and tomato sandwich,168,180,4.8,0.7,,8,25.9,2.8,0.9,N,2,1.3\n" +
            "ING,Egg and bacon malt brown sandwich,220,239,11.9,4.1,,14.8,18,1.9,1.3,N,2.1,1.8\n" +
            "ING,Honey Roast Salmon Sandwich,226,195,7.8,2.2,,10.6,20.7,2.4,0.6,N,2,2.5\n" +
            "ING,Roast Beef Sandwich,223,205,6.8,1.9,,13.2,22.3,4,1.1,N,2.5,2.5\n" +
            "ING,Smoked Salmon Cream Cheese Sandwich,192,225,13.6,4.1,,12.4,12.3,3,1.3,N,3.1,2.5\n" +
            "ING,Fish and vegetable meduim sushi,240,155,2.4,0.4,,4.9,28,10,0.8,N,2.2,3\n" +
            "ING,Fish and vegetable sushi select,151,150,1.5,0.2,,4.4,29,6.3,0.9,N,1.9,2\n" +
            "ING,Tesco Ham and Cheddar Sub,195,260,10,4.5,,14.9,27.6,2.6,1.3,N,1.5,2\n" +
            "ING,Ham and Cheese pickle brown bread sandwich,205,215,7.6,4.6,,12.2,24.5,3.3,1.2,N,1.8,1.6\n" +
            "ING,Ham Tripe Sandwich,224,230,9.9,1.8,,11.1,24.2,2.1,1.1,N,2.4,2\n" +
            "ING,Hoi Sin Duck Wrap,184,215,5.6,2.9,,12.6,28.4,1.3,1,N,3.4,2.3\n" +
            "ING,Tesco Just Ham Sandwich,117,205,4,2,,11.6,30.4,3.7,1.2,N,2.1,1.3\n" +
            "ING,LChoices Chicken and Stuffing Sandwich,173.1,175,2.4,0.3,,14,23.4,1.6,0.6,N,2.6,1.8\n" +
            "ING,LChoices Chicken Salad Sandwich,207,140,1.9,0.6,,13.9,16.8,1.6,0.3,N,2.7,1.8\n" +
            "ING,LChoices Egg Mayonnaise and Cress Sandwich,161,175,4.3,1.3,,9.5,24.2,1,0.7,N,2.7,1\n" +
            "ING,LChoices Ham and Cheese Sandwich,160,195,3.9,1.9,,14.3,25.2,1.9,1.2,N,2.9,1.6\n" +
            "ING,LChoices Ham salad  sandwich,193,135,1.4,0.4,,11,19.8,2.6,0.9,N,2.1,1.6\n" +
            "ING,LChoices Prawn Mayonnaise Sandwich,156,170,2.9,0.7,,11.7,23.2,0.9,1.2,N,1.6,1.3\n" +
            "ING,LChoices Salmon Cucumber Sandwich,155,169,2.6,0.6,,12.3,24.1,1.7,0.9,N,2.1,1.3\n" +
            "ING,Thai Chicken Sandwich,186,235,8.5,1.7,,10.7,28,6.9,0.9,N,2.4,2\n" +
            "ING,Veggie Mini Roll Selection,170,270,12.7,6.7,,10.2,28.2,4.6,1,N,3.7,2\n" +
            "ING,Meatball and Cheese Sub,252,265,12.1,4.4,,9.3,29.3,2.7,1,N,2.4,2\n" +
            "ING,Prawn Mayonnaise Oatmeal Bread Sandwich,250,265,12.1,4.4,,9.3,29.3,2.7,1,N,2.4,2\n" +
            "ING,Prawn Mayonnaise Sandwich Triple,214,210,6.8,1,,11.6,25.3,0.6,1.2,N,1.8,2\n" +
            "ING,Prawn Tunna and Vegetable Sushi,142,150,2,0.2,,3.9,29,6.8,0.9,N,2.1,1.3\n" +
            "ING,Red Cheddar and Tomato Sandwich,177,265,15.4,5.4,,9.2,22.4,1.2,0.8,N,2,1.3\n" +
            "ING,Roast Chicken and Stuffing Sandwich,200,225,7.2,0.7,,15,24.8,1.7,1,N,1.9,1.6\n" +
            "ING,Roast Chicken Sandwich,168,230,7.3,1.6,,14.7,26,1.9,0.6,N,2.1,1.6\n" +
            "ING,Salmon and Cucumber Sandwich,154,205,6.5,1.9,,11.8,24.8,1.5,1.1,N,1.8,1.3\n" +
            "ING,Salmon and Vegetable Sushi,142,147,2,0.3,,3.3,29,6.5,0.9,N,1.9,1.3\n" +
            "ING,Sausage and egg sandwich,175,215,7.3,1.7,,9.8,27.1,2.4,0.9,N,2.6,1\n" +
            "ING,Seafood Cocktail Sandwich,170,225,9.4,0.7,,5.7,2.5,2.8,1,N,2.8,1.5\n" +
            "ING,Smoked Ham and Cheddar Sandwich,176,270,13.2,5.5,,13.7,23.5,1.8,1.1,N,1.8,1.6\n" +
            "ING,Smoked Ham and Mustard Sandwich,161,260,13.7,1,,11.5,22.5,1.4,1.3,N,1.2,1.3\n" +
            "ING,Smoked Ham Saland and Salad Cream Sandwich,200,160,4,1.2,,10.1,20.8,2.5,1.1,N,3.1,1.6\n" +
            "ING,Southern Fried Chicken Sub,268.5,230,8.3,2.1,,9.4,29.1,1.5,1,N,1.7,2\n" +
            "ING,Southern Fried Chicken Wrap,214,210,6.7,2.3,,9.6,22.2,2.9,1,N,1.2,2\n" +
            "ING,Tuna and Cucumber Sandwich,200,205,9.8,0.8,,11.1,17.3,1.3,0.9,N,2.1,1\n" +
            "ING,Tuna Salad Sandwich,188,215,12,1,,9.5,16.5,2,0.5,N,1.6,1.5\n" +
            "ING,Tuna Sweetcorn Maltbread Sandwich,170,190,6.1,0.6,,9.7,24,0.7,0.8,N,2.9,1.3\n" +
            "ING,Veggie Sushi Pack,147,145,1.7,0.3,,2.8,29,7.1,0.9,N,2.4,2\n" +
            "ING,Broccoli and Stilton Soup ,300,56,3.7,2.3,,2.9,2.7,1.1,0.6,N,1.1,1.97\n" +
            "ING,Chicken Soup,300,94,5.9,2.6,,3.7,6.6,1.5,0.7,N,0.5,1.97\n" +
            "ING,Fresh Carrot and Coriander Soup,300,43,2.2,1.3,,0.6,5.2,3.7,0.7,N,1.2,1\n" +
            "ING,Lentil and Bacon Soup,300,77,1.9,0.3,,4.7,10.3,1.3,0.7,N,2.5,1.97\n" +
            "ING,Minestrone Soup ,300,37,0.9,0.1,,1.4,5.8,2.8,0.5,N,1.1,1.97\n" +
            "ING,Plum and Tomato and sweet basil soup,300,44,2,0.3,,1.3,5.2,4.9,0.3,N,1.3,1.97\n" +
            "ING,Smoked Haddock Chowder ,300,59,1.8,1.1,,2.3,8.3,1.5,0.5,N,0.9,2.19\n" +
            "ING,Soup of the Month Kedgree,300,55,1.5,0.9,,1.9,8.2,0.9,0.5,N,0.5,1.97\n" +
            "ING,Spicy butternut Squah and Sweet Potato,300,64,3.6,1.5,,1,6.9,2.1,0.5,N,1,2.19\n" +
            "ING,Summer Leek and Potato Soup,300,50,2,1.2,,1.2,6.8,1.1,0.6,N,0.8,1.97\n" +
            "ING,Vegetable Soup,300,54,1.1,0.5,,2.4,8.7,2.3,0.5,N,1.5,1.97\n" +
            "ING,Wild Mushroom Soup,300,38,1.6,0.9,,1.2,4.8,0.9,0.7,N,0.6,1.97\n" +
            "ING,Beef and Tomato Soup,300,65,4.1,1.5,,2.4,4.3,2.1,0.5,N,0.8,1.49\n" +
            "ING,Broccoli and Stilton Soup ,300,75,4.1,2.2,,4,4.5,1.8,0.4,N,1.1,1\n" +
            "ING,arrot and Coriander Soup,300,39,2.3,0.8,,0.7,3.9,3.7,0.4,N,1.5,0.75\n" +
            "ING,Chicken and Vegetable Soup ,300,38,1.2,0.3,,2.1,4.7,2.2,0.5,N,1,0.75\n" +
            "ING,Cream of Tomato Soup,300,65,3.2,1.2,,1.3,7.2,4.3,0.4,N,0.6,1\n" +
            "ING,Creamy Vegetable Soup,300,49,2.6,1.6,,1,5.6,2.5,0.5,N,1.6,1\n" +
            "ING,Pea and ham Soup,300,65,2.7,1.6,,3.4,5.9,3.1,0.5,N,1.6,1.95\n" +
            "ING,Puy Lentil and Tomato Soup,300,60,1.3,0.2,,2.8,9.2,3.5,0.4,N,1.5,1.95\n" +
            "ING,Thai Chicken Soup,300,300,85,3.2,,4.1,4.2,2.2,0.5,N,1.1,1.95\n" +
            "ING,Wild Mushroom Soup,300,85,5.6,2.9,,2.1,5.2,1.5,0.6,N,0.7,1.95\n" +
            "ING,Chicken Mulligatawny Soup ,300,80,4,1.6,,4,7,1.1,0.3,N,0.8,1.95\n" +
            "ING,French Onion Soup,300,41,1.4,0.8,,0.3,6.8,5,0.5,N,0.7,1.95\n" +
            "ING,Free Range Chicken Stock,125,13,0.3,0.1,,2.4,0.1,0.1,0.3,N,0,1.69\n" +
            "ING,Lamb Vegetable Scotch Broth Soup,300,43,1.8,0.6,,1.7,5,2.1,0.4,N,1.4,1.5\n" +
            "ING,Lentil and Bacon Soup,300,70,1.9,0.5,,3.9,8.5,3,0.6,N,1.6,0.75\n" +
            "ING,Soup of the Season Cauliflower and cheese,300,42,2.1,1.2,,0.8,5,3.4,0.4,N,2,1.5\n" +
            "ING,Poultry Gravy 550g,125,49,3.4,1.2,,1.1,3.7,1,1.1,N,0.4,1.69\n" +
            "ING,Tomato and Basil Soup,300,39,1,0.1,,1,6.5,4.5,0.1,N,0.7,1.29\n" +
            "ING,Vegetable Mulligatawny Soup ,300,70,2.5,1.3,,1.9,9,2.4,0.4,N,1.1,1.5\n" +
            "ING,Arrabbiato Sauce 350g,175,41,0.3,0,,1.3,8.3,6.6,0.9,N,1.1,1.4\n" +
            "ING,Tesco Beef Bolognese Sause 350,175,101,5.8,1.8,,5.4,6.9,5.3,0.9,N,0.8,1.37\n" +
            "ING,Chargrilled mediterranean vegetable sauce,175,50,1.1,0.2,,1.6,8.4,5.1,0.6,N,1.3,1.4\n" +
            "ING,Carbonara Sauce,175,120,7.9,5,,6.3,5.4,0.9,0.3,N,0.1,1.4\n" +
            "ING,Cheese Sauce,175,135,9,5.5,,5.6,7.2,1.2,0.6,N,0,1.4\n" +
            "ING,Creamy Tomato Sauce,150,90,5.1,3.3,,1.6,8.3,4.5,0.7,N,0.4,2\n" +
            "ING,Cherry Tomato Sauce 350g,175,70,2.6,0.5,,1.5,9.4,7.3,0.6,N,1.1,2\n" +
            "ING,Wild Mushroom Sauce 350g,175,125,10.3,4.9,,1.6,5.9,2.2,0.9,N,0.5,2\n" +
            "ING,Four cheese sauce 350g,175,160,13.7,8.7,,5.2,3.5,0.7,0.7,N,0,2\n" +
            "ING,Pancetta mushroom sauce 350g,175,105,6.9,4.3,,3.7,6.8,2.3,0.9,N,0.2,1.5\n" +
            "ING,Tomato and mascarpone sauce 350g,175,95,6.3,3.7,,2.1,6.8,3,1,N,0.7,2\n" +
            "ING,Green Pesto,50,502,49,10.9,,9.6,5.6,0.4,1.7,N,1.1,2\n" +
            "ING,Napoletana Sauce 350g,175,51,1.2,0.2,,1.5,8.5,6.3,1.2,N,1,1.4\n" +
            "ING,Tomato and Bacon Sauce 350g,175,55,2.5,0.8,,3,4.7,4.3,0.6,N,1,1.37\n" +
            "ING,Tomato Sauce ,150,55,1.7,0.2,,1.2,7.8,5,0.6,N,0.7,1.7\n" +
            "ING,Tomato and mascarpone sauce 350g,175,105,7.4,3.3,,2.6,7,3.9,1,N,0.7,1\n" +
            "ING,Free Range egg and spinach linguine,200,280,3.2,1,,11.2,50.8,1.3,0.1,N,3.8,1.8\n" +
            "ING,Four Cheese Tortelloni,150,270,7.9,3.6,,12.3,37.3,3,1,N,3.4,1.5\n" +
            "ING,Beef and Red wine ravioli ,150,315,12.2,4.5,,10,40.5,1.9,0.9,N,2.5,1.5\n" +
            "ING,Cheese and tomato basil tortelloni,150,200,6,2.8,,7.5,28,2.4,0.8,N,3.2,1.5\n" +
            "ING,Cheese and tomato Cappelletti,150,180,4.2,1.3,,6.5,28.7,1.7,0.8,N,2.2,2.99\n" +
            "ING,Chicken and bacon tortelloni,150,220,7.3,2.2,,7.8,29.8,1.1,0.8,N,2.1,1.5\n" +
            "ING,Disney Cheese and Spinach Pasta,100,165,4.9,2.8,,5.4,23.9,0.8,0.8,N,2,1.5\n" +
            "ING,Disney Ham Filled Pasta,100,170,3.3,2,,7.1,27.2,2.6,0.8,N,2,1.5\n" +
            "ING,Beef Cannelloni 360g,163,182,7.8,3.2,,13.2,14.8,3,0.7,N,3.6,2.99\n" +
            "ING,Chicken and Mozzarella Ravioli,150,245,9.3,2.8,,9.2,30.5,1.8,0.8,N,1.8,2.7\n" +
            "ING,Spinach Ricotta Cannelloni 360g,163,203,10.7,5.2,,8.2,18.5,1.6,0.7,N,3.4,2.99\n" +
            "ING,Sundried tomato and mozzarella Ravioli,150,285,11.4,5.2,,11.3,33.9,3.9,1.1,N,1.9,2.7\n" +
            "ING,Fresh egg fusilli,125,265,2,0.5,,10,51.7,2.3,0,N,2.7,1.3\n" +
            "ING,Free egg lasagne,62.5,265,2,0.5,,10,51.7,2.3,0,N,2.7,0.78\n" +
            "ING,Fresh Egg Penne,125,165,1.7,0.5,,7.5,30,0.9,0,N,2.5,1.3\n" +
            "ING,Fresh egg spagthetti,125,165,1.3,0.5,,5.1,33,0.6,1.3,N,1.8,1.3\n" +
            "ING,Fresh egg Tagliatelle,125,265,2,0.5,,10,51.7,2.3,0,N,2.7,1.3\n" +
            "ING,Fresh pasta shells,125,265,2,0.5,,10,51.7,2.3,0,N,2.7,1.3\n" +
            "ING,Gnocchi,125,155,0.1,0,,3.9,34.1,0.3,1.3,N,0.6,1.4\n" +
            "ING,Italian Sausage and Ham Tortelloni ,150,273,9.3,4.8,,13.1,34,2.7,1.2,N,3.7,1.5\n" +
            "ING,Smoked ham bacon and tomato tortelloni,150,205,6.7,2.7,,8.1,27,1.9,0.8,N,2,1.5\n" +
            "ING,Spinach and Ricotta Tortelloni,150,300,11.7,4.9,,10.5,37.4,2.3,1,N,2.5,1.5\n" +
            "ING,Trattoria Verdi Fettucine,125,265,1.5,0.4,,10,52,2.2,0,N,2.7,1.3\n" +
            "ING,Trattoria Verdi Italian Sausage Tortelloni,200,290,6,1.9,,11,48,2.2,1.3,N,2,1.3\n" +
            "ING,Finest 12 Tempura Battered King Prawns,17.5,175,9.1,3.8,,11.5,111,0.2,1,N,4,2\n" +
            "ING,Bowyers 12 Pack Egg and Bacon Bites,18,250,14,3.50,,7.6,2.3,1.5,1.5,N,1,0.94\n" +
            "ING,Debbie and Andrew Steak and Guiness Pie,100,276,15.5,6.57,,9.3,25,1.9,0.9,N,2,2.49\n" +
            "ING,Ginsters 4 Cornish Pasties,130,274,18.7,6,,5,21,1.5,1,N,2,2.49\n" +
            "ING,Ginsters 4 Steak Slice,105,274,14.5,5.8,,9.7,26.3,1.1,14.5,N,1.7,2.49\n" +
            "ING,Ginsters Deep Fill Chicken Mushroom Slice,100,439,26.8,12.5,,15,34.4,1.7,2.16,N,3.3,0.79\n" +
            "ING,Ginsters Deep Fill Pepper Steak Slice,100,455,27,12.7,,15.1,38.4,2.8,1.93,N,2.9,0.79\n" +
            "ING,Ginsters Large Cornish Pasty,100,549,32.2,14.8,,12.1,52.7,3.2,2.77,N,7.1,1.25\n" +
            "ING,Ginsters Large Sausage Roll,100,499,37.5,16.4,,13.1,27.2,1.1,1.79,N,2.9,1\n" +
            "ING,Ginsters Sneak and Onion Pie,100,481,30.8,12.6,,14.1,36.5,4,1.4,N,2.3,1\n" +
            "ING,Ginsters Steak Pie,100,433,24.7,10.5,,17.7,35,3.2,1.42,N,2.6,1\n" +
            "ING,Oaktree Estate Cheese and Onion Quiche,100,255,17.2,9.3,,6.6,18,0.9,0.4,N,2.3,1.49\n" +
            "ING,Oaktree Estate Giant Pastry ,210,315,19.3,9.2,,4.8,29.7,2,1.2,N,3,1.02\n" +
            "ING,Oaktree Estate Quiche Lorraine,100,250,16.7,8.3,,6.6,18,1.6,1,N,2.3,1.49\n" +
            "ING,Pork Farm 2 Pack Snack Pork Pies,100,323,23.7,9.4,,7.4,20.2,1,1.1,N,1.4,0.89\n" +
            "ING,Pork Farm Individual Pork Pie,100,580,43.4,17.1,,12.4,34.9,1.8,2,N,2.4,0.68\n" +
            "ING,Pukka Pie All Steak Pie,100,231,14.1,7,,9.9,16.2,0.5,1,N,1.6,1.45\n" +
            "ING,Pukka Pie Chicken and Mushroom Pie,100,210,12.9,5,,7.6,15.7,0.3,1,N,3.5,1.45\n" +
            "ING,Pukka Pie Steak and Kidney Pie,100,205,10.9,5.2,,9.1,17.7,0.4,0.95,N,3.6,1.45\n" +
            "ING,Mini Savoury Eggs 12,20,295,17.4,4.4,,9.8,24.2,0.7,1,N,1.4,1.09\n" +
            "ING,Mini Egg and Bacon Bites,18,290,18.2,4.5,,9.9,21.7,0.7,18.2,N,1.2,1.69\n" +
            "ING,2 Mincedd Steak and Onion Slices,165,285,18.3,8.1,,7.7,22.1,1.3,0.9,N,2.4,1.26\n" +
            "ING,2 Pack Cheese and Oniona Rolls 133g,66.5,290,18.5,9.4,,7,23.6,1.6,1,N,3.4,0.5\n" +
            "ING,2 Pack Chicken and Mushroom Slices 330g,165,295,20.9,7.7,,6.9,19.6,0.6,20.9,N,2.9,1.26\n" +
            "ING,2 Pack Chicken Tikka Slices 330g,165,267,16.3,8.2,,7.9,22.2,3.4,0.7,N,3.7,1.26\n" +
            "ING,2 Pack Ham and Cheese Slices 330g,165,275,18.8,6.6,,7,18.8,1.1,1,N,2.9,1.26\n" +
            "ING,2 Pack Roast Chicken and Ham Slices,165,290,19.5,8.6,,8.6,19.9,1.6,1,N,3.3,1.26\n" +
            "ING,2 Pork ang Egg Gala Slices,125,310,22.1,9.3,,11.3,16,1.8,1.1,N,1.1,1.38\n" +
            "ING,2 Snack Pork and Pickle Pork Pies,75,355,23.1,8.8,,8.3,27.9,4.3,0.8,N,2,1.25\n" +
            "ING,20 Bite Size Pastires,30,340,22.8,9.4,,7,26.8,2.3,0.4,N,3.1,1.49\n" +
            "ING,2 Scotch Eggs,113,235,13.9,4.5,,10.2,16.9,0.3,1,N,2.2,0.85\n" +
            "ING,4 Pack Cheese and Onion Pasty,150,280,17.6,9.3,,5.9,23.7,2.6,1.2,N,2.2,1.64\n" +
            "ING,40 Cooked Pork Cocktail Sausages 400g,50,275,19.5,7.1,,12.7,12,1.2,1.4,N,0.5,1.75\n" +
            "ING,5 Mini Pork Piclke Cheese meltons,50,380,25.3,9.7,,8.9,29.2,4.2,1.2,N,2,2.25\n" +
            "ING,Cheese and Onion Rolls,66.5,290,18.5,9.4,,7,23.6,1.6,1,N,3.4,1.49\n" +
            "ING,Cranberry Pork Pies,63,375,26.1,10.9,,7.4,27.5,6.5,1.2,N,1.8,1.37\n" +
            "ING,Snack Eggs,45,275,16.1,4.4,,9.6,21.8,0.7,1,N,1.5,1.69\n" +
            "ING,Bacon and Leek Quiche 400g,100,260,18.5,7.4,,6.4,16.8,1.8,0.5,N,3.3,1.88\n" +
            "ING,Bacon Sausage and Tomato Quiche,100,240,15.4,6.7,,6.5,18.3,1.7,1,N,0.7,1.88\n" +
            "ING,Bitesize Cornish Pasties,30,350,22.8,9.7,,7.1,28.6,2.8,1,N,2.4,1.68\n" +
            "ING,Broccoli Quiche,100,250,17.2,7.6,,6,17.6,3.5,0.5,N,1.4,1.88\n" +
            "ING,Cheese and Bacon Quiche,100,250,16.1,7.2,,7.6,17.9,1.7,0.8,N,0.7,1.88\n" +
            "ING,Cheese and Onion Pasty,150,280,17.6,9.3,,5.9,23.7,2.6,1.2,N,2.2,0.49\n" +
            "ING,Cheese and Onion Quiche,100,260,17.6,8,,7,17.5,1.6,0.5,N,1.6,1.88\n" +
            "ING,Cheese and Tomato Quiche,100,265,18.2,7.9,,6.4,18.6,3.8,0.3,N,1.8,1.88\n" +
            "ING,Chicken and Mushroom Pie,150,273,16.1,7.6,,7.1,24.8,1.2,0.8,N,2.2,0.58\n" +
            "ING,Cornish Pasty,150,280,16.6,6.7,,7.4,24.5,0.9,1,N,2.7,0.49\n" +
            "ING,Deep Fill Puff Chicken and Mushroom Pie,235,243,15.2,7.6,,7.4,19.2,1.6,0.8,N,1.4,1.55\n" +
            "ING,Deep Fill Puff Ham Hock and Leek Pie,235,237,15,7.6,,6.1,19.3,1.2,0.7,N,1.2,3.39\n" +
            "ING,Deep Fill Puff Lamb Hot Pot Pie,235,235,13.7,6.8,,10,17.9,1.2,0.8,N,1.2,3.39\n" +
            "ING,Deep Fill Puff Roast Chicken Pie,235,245,14.5,6.7,,10.1,18.6,1.3,1,N,1.4,1.55\n";


    private Food_Item_CIF4 myfooditem;
    private EditText mGo;
    private Context mAppContext = null;


    public Display_Dialog_CIF11(){
        super();
        myfooditem = new Food_Item_CIF4();}


    public void Set_mAppContext(Context mApp)
    {
        mAppContext = mApp;
    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.fragment_display__dialog__cif10,null))
                .setTitle(R.string.food_item_text)
                .setPositiveButton(android.R.string.ok,new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        mGo = (EditText) getDialog().findViewById(R.id.dialogtext1);
                        String resalt = mGo.getText().toString();
                        int wait = changeType(resalt);
                        myfooditem.Set_weight(wait);
                        myfooditem.Make_calorie_value();

                        boolean done = FoodItemsLab_CIF9.get(getActivity().getApplicationContext()).removeFoodItem(myfooditem);
                        done = FoodItemsLab_CIF9.get(getActivity().getApplicationContext()).setFoodItem(myfooditem);

                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();

    }

    public Food_Item_CIF4 Get_myfooditem(){return myfooditem;}
    public void Set_myfooditem(Food_Item_CIF4 setter){myfooditem = setter;}

    public int changeType(String tuchange)
    {
        int output = 0;
        try
        {
            output = Integer.parseInt(tuchange);

        }
        catch (NumberFormatException e)
        {
            WrongNumberFormatMsg();
        }

        return output;
    }

    public float changeTypeFloat(String tuchange)
    {

        float output = 0;
        try
        {
            output = Float.parseFloat(tuchange);
        }
        catch (NumberFormatException e)
        {
            WrongNumberFormatMsg();
        }

        return output;
    }

    public void WrongNumberFormatMsg()
    {
        String title = "Wrong Number Format";
        AlertDialog alert = new AlertDialog.Builder(getActivity()).create();
        alert.setTitle(title);
        alert.setMessage("You need to enter a correct format number that represents the weight (grams) of the food item.");
        alert.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface inter, int which)
            {
                onDestroyView();
            }
        });
        alert.show();

    }

    public void Showing(String tbs)
    {

        String title = "Countdown Report";


try
{
    AlertDialog alert = new AlertDialog.Builder(getActivity()).create();
    alert.setTitle(title);
    alert.setMessage(tbs);
    alert.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface inter, int which)
        {
            onDestroyView();
        }
    });
    alert.show();
}
catch(NullPointerException nulle)
{
    if(mAppContext != null)
    {
        AlertDialog alert = new AlertDialog.Builder(mAppContext).create();
        alert.setTitle(title);
        alert.setMessage(tbs);
        alert.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface inter, int which) {
                onDestroyView();

            }
        });
        alert.show();
    }
    else
    {

    }
}


    }

    public void ShowToast(int ResID)
    {
        Toast.makeText(getActivity(),ResID,Toast.LENGTH_SHORT).show();
    }


    public void SummaryBoxShowing(SummaryBoxCIF12 sumbox)
    {

        String title = "Countdown Report";


        try
        {
            AlertDialog alert = new AlertDialog.Builder(getActivity()).create();
            alert.setTitle(title);
            alert.setMessage(sumbox.GetSummaryString());
            alert.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface inter, int which)
                {

                    onDestroyView();
                }
            });
            alert.show();
        }
        catch(NullPointerException nulle)
        {
            if(mAppContext != null)
            {
                AlertDialog alert = new AlertDialog.Builder(mAppContext).create();
                alert.setTitle(title);
                alert.setMessage(sumbox.GetSummaryString());
                alert.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface inter, int which) {
                        onDestroyView();

                    }
                });
                alert.show();
            }
            else
            {

            }
        }


    }

    public void SummaryBoxShowingDebit(SummaryBoxCIF12 sumbox)
    {

        String title = "Countdown Report";


        try
        {
            AlertDialog alert = new AlertDialog.Builder(getActivity()).create();
            alert.setTitle(title);
            alert.setMessage(sumbox.GetDebitSummaryString());
            alert.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface inter, int which)
                {
                    onDestroyView();
                }
            });
            alert.show();
        }
        catch(NullPointerException nulle)
        {
            if(mAppContext != null)
            {
                AlertDialog alert = new AlertDialog.Builder(mAppContext).create();
                alert.setTitle(title);
                alert.setMessage(sumbox.GetDebitSummaryString());
                alert.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface inter, int which) {
                        onDestroyView();
                    }
                });
                alert.show();
            }
            else
            {

            }
        }


    }

    public void TakeInMonthlyStatementBOXFake(MIF22MonthlyStatement mStatement)
    {
        MIF22FillHerUp fillHerUp = new MIF22FillHerUp(mAppContext);
        Transactions_CIF22 IN = new Transactions_CIF22();
        IN = fillHerUp.FillHerUpVerb(IN);

        String Statement;

        IN = mStatement.Produce_Monthly_Statement_Fake(IN);
        IN = mStatement.MonthlyStatementMassageObject1(IN);

        Statement = mStatement.PrintOut();//mStatement.ProduceMonthlyStatement(IN.Get_TransactionLines());
        Show_Monthly_Statement_PrintOut(Statement);

    }

    public void TakeInMonthlyStatementBOXFake1(MIF22MonthlyStatement mStatement, java.util.Date Start_IN, java.util.Date Start_OUT)
    {

        Log.d("TAG", "Really Ok try this 1a");
        SQLDatabase_Food_Items_CIF6 jackie = new SQLDatabase_Food_Items_CIF6(mAppContext);
        //jackie.Insert_TransactionTable(new Transaction_CIF52());
        Log.d("TAG", "Ok try this 1b");
        //jackie.Insert_TransactionTable(new Transaction_CIF52("Bangers and Mash"));
        Log.d("TAG", "Really Ok try this2");
        //jackie.Insert_TransactionTable(new Transaction_CIF52());
        Log.d("TAG", "Really Ok try this4");

        Transactions_CIF22 test = new Transactions_CIF22();

        Log.d("TAG", "Really Ok try this5");

        Food_Item_CIF4 dummyrice2 = new Food_Item_CIF4();

        Log.d("TAG", "Really Ok try this6");

        dummyrice2.Set_category("ING");
        dummyrice2.Set_food_item_name("McDonalds : Burgers : Double Cheeseburger flo");
        dummyrice2.Set_grams_per_serving_portion((float)100.00);
        dummyrice2.Set_calories_per_100g((float)445.00);
        dummyrice2.Set_fat_per_100g((float)5.00);
        dummyrice2.Set_carbs_per_100g((float)5.00);
        dummyrice2.Set_protein_per_100g((float)50);

        BoxCIF17 food_items = new BoxCIF17();
        food_items.addFoodItem(dummyrice2);

        Log.d("TAG", "Really Ok try this7");

        //Food_Item_CIF4 dummyrice3 = new Food_Item_CIF4();

        Log.d("TAG", "Really Ok try this8");

        //dummyrice3.Set_category("ING");
        //dummyrice3.Set_food_item_name("McDonalds : Burgers : French fries");

        //dummyrice3.Set_grams_per_serving_portion(100);
        //dummyrice3.Set_calories_per_100g(337);
        //dummyrice3.Set_fat_per_100g(5);
        //dummyrice3.Set_carbs_per_100g(5);
        //dummyrice3.Set_protein_per_100g(5);

        Log.d("TAG", "Really Ok try this9");

        Transaction_Line_CIF17 line = new Transaction_Line_CIF17();

        Log.d("TAG", "Really Ok try this10");

        line.Set_Transaction_Balance(500);
        line.Set_Transaction_Meal_Type("Dunch flo");
        line.Set_Calorie_Outwards(0);
        line.Set_Calorie_Out(0);
        line.Set_Transaction_Meal_Box_ID(88);
        line.Set_Transaction_Meal_Box_Food_Type("EATOUT");
        line.Set_Transaction_DateX(new Date().getTime());
        line.Set_Calorie_IN(600);
        line.Set_Description("www.ese-edet.eu ");
        line.Set_New_Balance(8000);
        line.Set_Transaction_Amount(441);
        line.Set_Transaction_Food_Items(food_items);
        line.Set_Transaction_Meal_Type_ID(777);
        line.Set_Transaction_ID(7777777);
        //line.add_Food_item(dummyrice2);

        Log.d("TAG", "Really Ok try this11");

        Transaction_CIF52 Line_52 = new Transaction_CIF52();
        Log.d("TAG", "Really Ok try this12");
        Line_52.Set_Single_Transaction_Line(line);
        Log.d("TAG", "Really Ok try this13");
        test.Add_TransactionLine_2_List(Line_52);
        Log.d("TAG", "Really Ok try this14");


        Log.d("TAG", "Really Ok try this15");
        //jackie.Insert_TransactionTable_x(test);


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //MIF22FillHerUp fillHerUp = new MIF22FillHerUp(mAppContext);
        //Transactions_CIF22 IN = new Transactions_CIF22();
        //IN = fillHerUp.FillHerUpVerb(IN);

        String Statement = "";

        //IN = mStatement.Produce_Monthly_Statement_Real(IN, Start_IN, Start_OUT, new MIF4_Data_Model_Adapter(mAppContext));
        //IN = mStatement.MonthlyStatementMassageObject(IN);

        //Statement = mStatement.PrintOut();//mStatement.ProduceMonthlyStatement(IN.Get_TransactionLines());

        //Show_Monthly_Statement_PrintOut(Statement);


////////////////////////////////////////////////////////////////////////////

        Log.d("TAG", "Really Ok try this15b");

        Transactions_CIF22 The_One = jackie.Really_Get_All_Transactions();
        Log.d("TAG", "Really Ok try this15c");

        Statement = The_One.Print();
        Log.d("TAG", "Really Ok try this15d");


        //jackie.Delete_Meal_Box_Table();
        //jackie.Delete_Transaction_Table();

        Log.d("TAG FINAL", Statement );

        //MIF22MonthlyStatement mmStatement = new MIF22MonthlyStatement();

        Log.d("TAG", "Really Ok try this17");

        //The_One = mmStatement.MonthlyStatementMassageObject1(The_One);

        //Log.d("TAG", "Really Ok try this18 : Size of The_One " + new RoundingCIF13().IntToString(The_One.Get_TransactionLines().size()) + " Size of Transaction Lines " + new RoundingCIF13().IntToString((int)(The_One.Get_TransactionLines().get(0).Get_Single_Transaction_Line()).Get_Transaction_ID()));

        //Show_Monthly_Statement_PrintOut(The_One.Print());

        Log.d("TAG", "Really Ok try this19");




    }

    public void Show_Monthly_Statement_PrintOut(String IN)
    {
        //First check if mStatement is in and not null;
        if(IN != null)
        {
            Showing(IN);
        }
    }

    public boolean Populate_SQLite_Datebase()
    {

        //File file = mAppContext.getFileStreamPath("/lokeke/AndroidStudioProjects/CalorieCountdownAppforAndroid/app/src/main/java/ese/com/caloriecountdownforandroid/Volume1.txt");
        //if(file.exists())
        //{
        //    Toast.makeText(mAppContext, "File exists in /mnt", Toast.LENGTH_SHORT);
        //}
        //else
       // {
       //     Toast.makeText(mAppContext, "File NOT exists in /mnt", Toast.LENGTH_SHORT);
       // }



        //java.io.File file = new File("/lokeke/AndroidStudioProjects/CalorieCountdownAppforAndroid/app/src/main/java/ese/com/caloriecountdownforandroid/Volume1.txt");
        //java.io.File file2 = new File("Volume1.txt");

        java.io.File genericfile = new File("Volume1.txt");
        String filepath = "//lokeke/Volume1.txt";//"//lokeke/AndroidStudioProjects/CalorieCountdownAppforAndroid/app/src/main/java/ese/com/caloriecountdownforandroid/Volume1.txt";
        //String filepath2 = "Volume1.txt";

        Populate_SQLDatabase_Food_Items_CIF7 GenPop = new Populate_SQLDatabase_Food_Items_CIF7(filepath, Volume1, mAppContext); //("/lokeke/AndroidStudioProjects/CalorieCountdownAppforAndroid/app/src/main/java/ese/com/caloriecountdownforandroid/Volume1.txt", mAppContext);
        boolean result = GenPop.Populate();

        if(result)
        {
            Showing("Internal Database was Successfully Populated with Food items Data and Activity Data");
        }
        else
        {
            Showing("Internal Database was not Successfully Populated.");
        }

        return result;
    }

    public void Delete_Database()
    {


        Populate_SQLDatabase_Food_Items_CIF7 GenPop = new Populate_SQLDatabase_Food_Items_CIF7("no file", Volume1,mAppContext);
        Showing("Food items database is being deleted");
        GenPop.Delete_Database();
    }

    private String Massage_Fooditems(ArrayList<Food_Item_CIF4> INPUT)
    {
        String OUT ="\n";

        for(Food_Item_CIF4 m : INPUT)
        {
            OUT = OUT + m.Get_food_item_name();
        }

        return OUT;
    }


}
