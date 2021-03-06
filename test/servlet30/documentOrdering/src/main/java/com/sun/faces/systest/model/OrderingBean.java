/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package com.sun.faces.systest.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import javax.faces.bean.ManagedBean;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.faces.FactoryFinder;
import javax.faces.event.PhaseListener;

@ManagedBean
public class OrderingBean {

    public boolean isOrderCorrect() {

        LifecycleFactory factory = (LifecycleFactory) FactoryFinder.getFactory(FactoryFinder.LIFECYCLE_FACTORY);
        Lifecycle l = factory.getLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE);
        PhaseListener[] listeners = l.getPhaseListeners();
        List<PhaseListener> list = new ArrayList<PhaseListener>();
        for (PhaseListener listener : listeners) {
            if (listener.getClass().getName().contains("com.sun.faces.systest.PhaseListener")) {
                list.add(listener);
            }
        }
        listeners = list.toArray(new PhaseListener[list.size()]);
        String[] suffixes = { "C", "B", "A", "D"};
        if (listeners.length != 4) {
            System.out.println("INCORRECT LISTENER COUNT");
            return false;
        }
        for (int i = 0; i < listeners.length; i++) {
            if (!listeners[i].getClass().getName().endsWith(suffixes[i])) {
                System.out.println("INCORRECT DOCUMENT ORDERING: " + Arrays.toString(listeners));
                return false;
            }
        }

        return true;

    }
}
