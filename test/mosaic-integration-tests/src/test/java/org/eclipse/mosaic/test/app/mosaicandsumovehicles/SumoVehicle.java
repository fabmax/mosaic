/*
 * Copyright (c) 2020 Fraunhofer FOKUS and others. All rights reserved.
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contact: mosaic@fokus.fraunhofer.de
 */

package org.eclipse.mosaic.test.app.mosaicandsumovehicles;

import org.eclipse.mosaic.fed.application.ambassador.SimulationKernel;
import org.eclipse.mosaic.fed.application.app.AbstractApplication;
import org.eclipse.mosaic.fed.application.app.api.VehicleApplication;
import org.eclipse.mosaic.fed.application.app.api.os.VehicleOperatingSystem;
import org.eclipse.mosaic.lib.objects.vehicle.VehicleData;
import org.eclipse.mosaic.lib.util.scheduling.Event;
import org.eclipse.mosaic.rti.TIME;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SumoVehicle extends AbstractApplication<VehicleOperatingSystem> implements VehicleApplication {

    /**
     * Sample interval. Unit: [ns].
     */
    private final static long TIME_INTERVAL = 20 * TIME.SECOND;

    private void sample() {
        // create a new simple event to sample something in a specific interval
        getOs().getEventManager().addEvent(getOs().getSimulationTime() + TIME_INTERVAL, this);
    }

    @Override
    public void onStartup() {
        getLog().infoSimTime(this, "Startup: I'm a vehicle defined in SUMO route file.");
        sample();
    }

    @Override
    public void onShutdown() {
        getLog().infoSimTime(this, "Shutdown: I'm a vehicle defined in SUMO route file.");
        getLog().infoSimTime(this, "{} routes are known to the SimulationKernel.", SimulationKernel.SimulationKernel.getRoutesView().size());
    }

    @Override
    public void processEvent(Event event) throws Exception {
        getLog().infoSimTime(this, "I'm still here!");

        sample();
    }

    @Override
    public void onVehicleUpdated(@Nullable VehicleData previousVehicleData, @Nonnull VehicleData updatedVehicleData) {
        if (previousVehicleData == null) {
            getLog().infoSimTime(this, "I can read my route: {}", getOs().getNavigationModule().getCurrentRoute());
        }
    }
}
