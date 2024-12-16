package com.client;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.converter.service.UnitConversionService;

import java.util.Collection;

public class Activator implements BundleActivator {
    @Override
    public void start(BundleContext context) throws Exception {
        // Length conversion
        Collection<ServiceReference<UnitConversionService>> lengthRefs = context.getServiceReferences(UnitConversionService.class, "(unitType=length)");
        if (!lengthRefs.isEmpty()) {
            ServiceReference<UnitConversionService> lengthRef = lengthRefs.iterator().next();
            UnitConversionService lengthService = context.getService(lengthRef);
            double lengthResult = lengthService.convert(1000, "meters", "kilometers");
            System.out.println("1000 meters to kilometers: " + lengthResult);
        }

        // Weight conversion
        Collection<ServiceReference<UnitConversionService>> weightRefs = context.getServiceReferences(UnitConversionService.class, "(unitType=weight)");
        if (!weightRefs.isEmpty()) {
            ServiceReference<UnitConversionService> weightRef = weightRefs.iterator().next();
            UnitConversionService weightService = context.getService(weightRef);
            double weightResult = weightService.convert(2, "kilograms", "pounds");
            System.out.println("2 kilograms to pounds: " + weightResult);
        }

        // Temperature conversion
        Collection<ServiceReference<UnitConversionService>> tempRefs = context.getServiceReferences(UnitConversionService.class, "(unitType=temperature)");
        if (!tempRefs.isEmpty()) {
            ServiceReference<UnitConversionService> tempRef = tempRefs.iterator().next();
            UnitConversionService tempService = context.getService(tempRef);
            double tempResult = tempService.convert(25, "celsius", "fahrenheit");
            System.out.println("25 Celsius to Fahrenheit: " + tempResult);
        }
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println("Client stopped");
    }
}
