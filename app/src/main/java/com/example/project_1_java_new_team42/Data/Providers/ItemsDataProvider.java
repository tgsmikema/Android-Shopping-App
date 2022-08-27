package com.example.project_1_java_new_team42.Data.Providers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.project_1_java_new_team42.Models.Category;
import com.example.project_1_java_new_team42.Models.Desktop;
import com.example.project_1_java_new_team42.Models.IItem;
import com.example.project_1_java_new_team42.Models.Laptop;
import com.example.project_1_java_new_team42.Models.Tablet;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class ItemsDataProvider {

    public static final int NUMBER_OF_IMAGES_PER_ITEM = 4;


    public static List<List<List<String>>> getItemsInfoList() {
        //laptop - tablet - desktop
        List<List<List<String>>> itemsInfoList = new ArrayList<>();

        // laptop
        List<List<String>> laptopsInfoList = new ArrayList<>();
        laptopsInfoList.add(Arrays.asList("Apple MacBook Air 2020", Category.LAPTOP,"1299","Screen Size:  13.3 Inches\nColor:  Space Gray\nHard Disk Size:  256 GB\nRam:  8 GB\n\nApple-designed M1 chip for a giant leap in CPU, GPU, and machine learning performance\n","true"));
        laptopsInfoList.add(Arrays.asList("Apple MacBook Pro 2022",Category.LAPTOP,"1899","Screen Size:  13.3 Inches\nColor:  Space Gray\nHard Disk Size:  256 GB\nRam:  8 GB\n\nSUPERCHARGED BY M2 — The 13-inch MacBook Pro laptop is a portable powerhouse. Get more done faster with a next-generation 8-core CPU, 10-core GPU and up to 24GB of unified memory.\n","true"));
        laptopsInfoList.add(Arrays.asList("Alienware M15 R5 Gaming Laptop",Category.LAPTOP,"3699","Screen Size:  15.6 Inches\nColor:  Black, Dark Side of the Moon\nHard Disk Size:  2 TB\nRam:  32 GB\n\n[Processor] AMD Ryzen R9 5900HX (8-Core, 20MB Total Cache, up to 4.6 GHz Max Boost Clock)| NVIDIA GeForce RTX 3070 8GB GDDR6\n","true"));
        laptopsInfoList.add(Arrays.asList("ASUS VivoBook Pro",Category.LAPTOP,"2399","Screen Size:  16 Inches\nColor:  Comet Grey\nHard Disk Size:  1 TB\nRam:  32 GB\n\nComplimentary 3-month Adobe Creative Cloud subscription with the purchase. Learn more on ASUS website for more details\n", "false"));
        laptopsInfoList.add(Arrays.asList("Lenovo ThinkPad P17",Category.LAPTOP,"3999","Screen Size:  17.3 Inches\nColor:  Black\nHard Disk Size:  2 TB\nRam:  64 GB\n\nProcessor: 11th Generation Intel Core i7-11850H (8C / 16T, 2.5 / 4.8GHz, 24MB)\n","false"));
        laptopsInfoList.add(Arrays.asList("Dell Inspiron 15",Category.LAPTOP,"899","Screen Size:  15.6 Inches\nColor:  Black\nHard Disk Size:  1 TB\nRam:  16 GB\n\n[11th Intel Core i7-1165G7] 11th Generation Intel Core i7-1165G7 Processor (4 Cores, 8 Threads, 12MB Intel Smart Cache, Base Frequency at 2.80 GHz, Up to 4.70 Ghz at Maximum Turbo Speed)\n","true"));
        laptopsInfoList.add(Arrays.asList("Chuwi Chromebook",Category.LAPTOP,"399","Screen Size:  14 Inches\nColor:  Gray\nHard Disk Size:  256 GB\nRam:  8 GB\n\n[14 inch Micro-edge Touch Screen] Full-laminated anti-glare display, 10-finger multi-touch support, 300 nits bright view, 100% sRGB.\n","true"));
        laptopsInfoList.add(Arrays.asList("Samsung Galaxy Chromebook",Category.LAPTOP,"999","Screen Size:  13.3 Inches\nColor:  Fiesta Red\nHard Disk Size:  128 GB\nRam:  16 GB\n\nAwarded Best Chromebook by CNN Underscored!\n","true"));
        laptopsInfoList.add(Arrays.asList("HP EliteBook 840",Category.LAPTOP,"1499","Screen Size:  14 Inches\nColor:  Silver\nHard Disk Size:  512 GB\nRam:  16 GB\n\n[Upgraded]  Seal is opened for Hardware/Software upgrade only to enhance performance. 14.0 Inches Full HD (1920x1080) 60Hz IPS Display; Wi-Fi 6 AX201 Wifi, BT 5.2, Integrated Webcam, Fingerprint Security System, Backlit.\n","true"));
        laptopsInfoList.add(Arrays.asList("MSI Raider 2022",Category.LAPTOP,"2799","Screen Size:  17.3 Inches\nColor:  Black\nHard Disk Size:  1 TB\nRam:  32 GB\n\nDisplay: 17.3 Inches QHD (2560 x 1440) Anti-Glare Wide View Angle 240Hz 3ms DCI-P3 100%\n","true"));
        laptopsInfoList.add(Arrays.asList("Acer Predator Triton 500",Category.LAPTOP,"2999","Screen Size:  16 Inches\nColor:  Gray\nHard Disk Size:  1 TB\nRam:  32 GB\n\nExtreme Performance: Compete or create with impressive power and speed of the 12th Generation Intel Core i9-12900H processor, featuring 14 cores and 20 threads to divide and conquer any creative task or run your most intensive games\n","true"));
        laptopsInfoList.add(Arrays.asList("Toshiba Dynabook Pro",Category.LAPTOP,"1299","Screen Size:  15.6 Inches\nColor:  Black\nHard Disk Size:  512 GB\nRam:  16 GB\n\nIntel 10th Gen Core i7-10510U (4 Cores 8 Threads, base clock speed 1.8GHz, max turbo to 4.9GHz, 8MB Cache); 16GB DDR4 RAM; 512GB M.2 PCIe SSD\n","true"));

        // tablets
        List<List<String>> tabletsInfoList = new ArrayList<>();
        tabletsInfoList.add(Arrays.asList("Apple iPad Air 2022",Category.TABLET,"999","Screen Size:  10.9 Inches\nColor:  Purple\nHard Disk Size:  256 GB\nRam:  8 GB\n\n10.9-inch Liquid Retina display with True Tone, P3 wide color, and an antireflective coating\n","true"));
        tabletsInfoList.add(Arrays.asList("SAMSUNG Galaxy Tab A8",Category.TABLET,"499","Screen Size:  10.5 Inches\nColor:  Silver\nHard Disk Size:  138 GB\nRam:  4 GB\n\nA SCREEN EVERYONE WILL LOVE: Whether your family is streaming or video chatting with friends, the Galaxy Tab A8 tablet brings out the best in every moment on a 10.5 Inches LCD screen\n","true"));
        tabletsInfoList.add(Arrays.asList("Apple iPad Pro",Category.TABLET,"1599","Screen Size:  13.3 Inches\nColor:  Space Gray\nHard Disk Size:  256 GB\nRam:  8 GB\n\n12.9-inch edge-to-edge Liquid Retina display with ProMotion, True Tone, and P3 wide color\n\nA12Z Bionic chip with Neural Engine\n","false"));
        tabletsInfoList.add(Arrays.asList("Lenovo ThinkPad Tablet X12",Category.TABLET,"1099","Screen Size:  12.3 Inches\nColor:  Black\nHard Disk Size:  512 GB\nRam:  16 GB\n\n11th Generation Intel Core i5-1130G7 Processor (1.80 GHz, up to 4.00 GHz with Turbo Boost, 4 Cores, 8 Threads, 8 MB Cache)\n","false"));
        tabletsInfoList.add(Arrays.asList("SamSung Galaxy Tab S7",Category.TABLET,"1199","Screen Size:  11 Inches\nColor:  Black\nHard Disk Size:  128 GB\nRam:  8 GB\n\nINDUSTRIES FIRST 5G TABLET! (Supports AT&T Network Only)\n","true"));
        tabletsInfoList.add(Arrays.asList("Lenovo Yoga Tab 3",Category.TABLET,"299","Screen Size:  10.1 Inches\nColor:  Black\nHard Disk Size:  32 GB\nRam:  3 GB\n\nAndroid tablet has a 10.1” Full HD IPS crystal-clear display perfect for binge-watching TV and movies on-the-go\n","true"));
        tabletsInfoList.add(Arrays.asList("Dell Latitude Rugged Tablet",Category.TABLET,"899","Screen Size:  11.6 Inches\nColor:  Black\nHard Disk Size:  128 GB\nRam:  8 GB\n\n11.6-Inch Glove-Capable for Touchscreen FHD (1920x1080) with Corning Gorilla Glass\n","true"));
        tabletsInfoList.add(Arrays.asList("Microsoft Surface Pro 8",Category.TABLET,"1499","Screen Size:  13 Inches\nColor:  Silver\nHard Disk Size:  512 GB\nRam:  16 GB\n\nThe power of a laptop with the flexibility of a tablet, and every angle in between, with 13” touchscreen, iconic built-in Kickstand, and detachable Keyboard.\n","false"));
        tabletsInfoList.add(Arrays.asList("Usuma Tablet",Category.TABLET,"899","Screen Size:  10.1 Inches\nColor:  Gold\nHard Disk Size:  32 GB\nRam:  16 GB\n\n[High Performance & Fast Response] 10.1 inch tablet runs the latest system. Configured with excellent 10.1 Inches16GB - Octa Core 1.3GHz, GSM/WCDMA, running fast and more responsive.\n","false"));
        tabletsInfoList.add(Arrays.asList("HP ElitePad 1000",Category.TABLET,"1099","Screen Size:  10.1 Inches\nColor:  White/Black\nHard Disk Size:  128 GB\nRam:  4 GB\n\n10.1 inches Display\nIntel A-Series Quad-Core A10-4655M 1.6 GHz (2 MB Cache)\n","true"));
        tabletsInfoList.add(Arrays.asList("Apple iPad Mini 2021",Category.TABLET,"899","Screen Size:  8.3 Inches\nColor:  Space Gray\nHard Disk Size:  64 GB\nRam:  4 GB\n\n8.3-inch Liquid Retina display with True Tone and wide color\n\nA15 Bionic chip with Neural Engine\n","true"));
        tabletsInfoList.add(Arrays.asList("Amazon Fire HD 10 tablet",Category.TABLET,"399","Screen Size:  10.1 Inches\nColor:  Black\nHard Disk Size:  64 GB\nRam:  3 GB\n\nFast and responsive - powerful octa-core processor and 3 GB RAM. 50% more RAM than previous generation.\n","true"));


        // desktop
        List<List<String>> desktopsInfoList = new ArrayList<>();
        desktopsInfoList.add(Arrays.asList("iBUYPOWER Pro Gaming PC Computer Desktop",Category.DESKTOP,"3319","Brand:  IBUYPOWER\nCPU Model:  Core i7\nHard Disk Description:  SSD\nMemory Storage Capacity:  1 TB\nRam Memory Installed Size:  16 GB\nOperating System:  Windows 10\n","true"));
        desktopsInfoList.add(Arrays.asList("Skytech Shadow 3.0 Gaming PC Desktop",Category.DESKTOP,"1599","Brand:  Skytech Gaming\nCPU Model:  Ryzen 5 3600\nHard Disk Description:  SSD\nMemory Storage Capacity:  1 TB\nRam Memory Installed Size:  16 GB\nOperating System:  Windows 10\n","true"));
        desktopsInfoList.add(Arrays.asList("HP Elite Desktop PC Computer",Category.DESKTOP,"199","Brand:  HP\nCPU Model:  Core i5\nHard Disk Description:  HDD\nMemory Storage Capacity:  1 TB\nRam Memory Installed Size:  8 GB\nOperating System:  Windows 10\n","true"));
        desktopsInfoList.add(Arrays.asList("Dell Inspiron 3910 Desktop Computer Tower",Category.DESKTOP,"739","Brand:  Dell\nCPU Model:  Core i5\nHard Disk Description:  SSHD\nMemory Storage Capacity:  1000 GB\nRam Memory Installed Size:  16 GB\nOperating System:  Windows 11 Home\n","false"));
        desktopsInfoList.add(Arrays.asList("Dell XPS 8940 Desktop Computer Tower",Category.DESKTOP,"1199","Brand:  Dell\nScreen Size:  19.5 Inches\nCPU Model	Core i3	\nHard Disk Description:  SSD\nMemory Storage Capacity:  512 GB\nRam Memory Installed Size:  16 GB\nOperating System:  Windows 10 Pro\n","false"));
        desktopsInfoList.add(Arrays.asList("Dell OptiPlex 9020 Small Form Computer Desktop PC",Category.DESKTOP,"279","Brand:  Dell\nCPU Model:  Intel Core i7\nHard Disk Description:  SSD\nMemory Storage Capacity:  1 TB\nRam Memory Installed Size:  32 GB\nOperating System:  Windows 10 Home\n","false"));
        desktopsInfoList.add(Arrays.asList("YEYIAN SAI X11 Gaming PC Desktop Computer",Category.DESKTOP,"849","Brand:  YEYIAN\nCPU Model:  Core i5\nHard Disk Description:  SSD\nMemory Storage Capacity:  500 GB\nRam Memory Installed Size:  8 GB\nOperating System:  Windows 11 Home\n","false"));
        desktopsInfoList.add(Arrays.asList("HP Envy Desktop Computer",Category.DESKTOP,"1499","Brand:  HP\nCPU Model:  Core i7\nHard Disk Description:  SSD\nMemory Storage Capacity:  512 GB\nRam Memory Installed Size:  16 GB\nOperating System:  Windows 11 Home\n","false"));
        desktopsInfoList.add(Arrays.asList("2022 Newest HP Pavilion Desktop",Category.DESKTOP,"749","Brand:  HP\nCPU Model:  Intel Core i5\nHard Disk Description:  SSD\nMemory Storage Capacity:  1 TB\nRam Memory Installed Size:  16 GB\nOperating System:  Windows 11 Home\n","false"));
        desktopsInfoList.add(Arrays.asList("HP All-in-One 27 inches Desktop Computer",Category.DESKTOP,"1269","Brand:  HP\nScreen Size:  27 Inches\nCPU Model:  Intel Core i7-1165G7\nHard Disk Description:  SSD\nMemory Storage Capacity:  512 GB\nRam Memory Installed Size:  16 GB\nOperating System:  Windows 11 Home\n","false"));
        desktopsInfoList.add(Arrays.asList("Skytech Archangel Gaming Computer PC Desktop",Category.DESKTOP,"1299","Brand:  Skytech Gaming\nCPU Model:  Ryzen 5 3600\nHard Disk Description:  SSD\nMemory Storage Capacity:  500 GB\nRam Memory Installed Size:  16 GB\nOperating System:  Windows 10 Home\n","false"));
        desktopsInfoList.add(Arrays.asList("MSI PRO AP241 AIO Desktop",Category.DESKTOP,"799","Brand:  MSI\nScreen Size:  23.8 Inches\nCPU Model:  Core i5\nHard Disk Description:  SSD\nMemory Storage Capacity:  250 GB\nRam Memory Installed Size:  8 GB\nOperating System:  Windows 10 Home\n","false"));


        itemsInfoList.add(laptopsInfoList);
        itemsInfoList.add(tabletsInfoList);
        itemsInfoList.add(desktopsInfoList);


        return itemsInfoList;
    }

    public static List<IItem> getItems() {
        List<List<List<String>>> itemsInfoList = new ArrayList<>();
        itemsInfoList = getItemsInfoList();

        List<IItem> itemsList = new ArrayList<>();


        for (int i = 0; i < itemsInfoList.size(); i++){
            for (int index = 0; index < itemsInfoList.get(i).size(); index++) {

                List<String> itemsInfo = itemsInfoList.get(i).get(index);

                String id = itemsInfo.get(1).toLowerCase() + "_" + String.valueOf(index + 1);
                String name = itemsInfo.get(0);
                List<String> imagePaths = generateImagePaths(id);
                String category = itemsInfo.get(1);
                int price = Integer.parseInt(itemsInfo.get(2));
                //int totalSold = Integer.parseInt(itemsInfo.get(4));
                //initial total sold = 0
                int totalSold = 0;
                String description = itemsInfo.get(3);
                boolean generalProperty = Boolean.parseBoolean(itemsInfo.get(4));

                IItem item;

                if (category.equalsIgnoreCase(Category.LAPTOP)) {
                    item = new Laptop(id, name, imagePaths, category, price, totalSold, description, generalProperty);
                    itemsList.add(item);
                } else if (category.equalsIgnoreCase(Category.DESKTOP)) {
                    item = new Desktop(id, name, imagePaths, category, price, totalSold, description, generalProperty);
                    itemsList.add(item);
                } else if (category.equalsIgnoreCase(Category.TABLET)) {
                    item = new Tablet(id, name, imagePaths, category, price, totalSold, description, generalProperty);
                    itemsList.add(item);
                } else {
                    throw new UnsupportedOperationException("MAKE SURE YOU ENTERED A VALID CATEGORY!");
                }
            }
        }
        return itemsList;
    }

    public static List<String> generateImagePaths(String id){
        List<String> imagePaths = new ArrayList<>();
        for (int i = 1; i <= NUMBER_OF_IMAGES_PER_ITEM; i++){
            imagePaths.add(id + "_" + i);
        }
        return imagePaths;
    }

    public static void addItemsToFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<IItem> itemList = getItems();
        System.out.println(itemList.get(0).getDescription() + "----------------------------------------------------------");
        for (IItem aItem : itemList) {
            db.collection("items").document(aItem.getId()).set(aItem).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Log.d("Items Collection Add", "item " + aItem.getId() + " added.");
                    System.out.println("ADDED!!!!");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w("Items Collection Add", "item " + aItem.getId() + " NOT added.");
                    System.out.println("NOT ADDED!!!!");
                }
            });
        }
    }



}
