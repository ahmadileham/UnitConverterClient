package com.client;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.converter.service.UnitConversionService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class Activator implements BundleActivator {
    private Scanner scanner = new Scanner(System.in);
    private List<String> conversionHistory = new ArrayList<>();

    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println("Unit Conversion Application Started!");
        boolean running = true;

        while (running) {
            // Display menu
            System.out.println("\nChoose a conversion type:");
            System.out.println("1. Length");
            System.out.println("2. Weight");
            System.out.println("3. Temperature");
            System.out.println("4. View Conversion History");
            System.out.println("q. Quit");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    performConversion(context, "length");
                    break;
                case "2":
                    performConversion(context, "weight");
                    break;
                case "3":
                    performConversion(context, "temperature");
                    break;
                case "4":
                    displayHistory();
                    break;
                case "q":
                    running = false;
                    System.out.println("Exiting the application. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void performConversion(BundleContext context, String unitType) {
        try {
            Collection<ServiceReference<UnitConversionService>> serviceRefs =
                    context.getServiceReferences(UnitConversionService.class, "(unitType=" + unitType + ")");
            
            if (serviceRefs == null || serviceRefs.isEmpty()) {
                System.out.println("No converter available for " + unitType);
                return;
            }

            ServiceReference<UnitConversionService> serviceRef = serviceRefs.iterator().next();
            UnitConversionService converter = context.getService(serviceRef);

            // Take user input for conversion
            System.out.print("Enter the value to convert: ");
            double value = Double.parseDouble(scanner.nextLine());
            System.out.print("Enter the source unit: ");
            String sourceUnit = scanner.nextLine();
            System.out.print("Enter the target unit: ");
            String targetUnit = scanner.nextLine();

            // Perform conversion
            double result = converter.convert(value, sourceUnit, targetUnit);
            String conversionEntry = value + " " + sourceUnit + " = " + result + " " + targetUnit;
            System.out.println(conversionEntry);

            // Store in history
            conversionHistory.add(unitType + " Conversion: " + conversionEntry);

        } catch (Exception e) {
            System.out.println("Error during conversion: " + e.getMessage());
        }
    }

    private void displayHistory() {
        if (conversionHistory.isEmpty()) {
            System.out.println("No conversions have been performed yet.");
        } else {
            System.out.println("\nConversion History:");
            for (String entry : conversionHistory) {
                System.out.println(entry);
            }
        }
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println("Client stopped");
        scanner.close();
    }
}
