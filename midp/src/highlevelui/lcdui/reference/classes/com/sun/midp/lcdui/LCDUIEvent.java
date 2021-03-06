/*
 *   
 *
 * Copyright  1990-2009 Sun Microsystems, Inc. All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License version
 * 2 only, as published by the Free Software Foundation.
 * 
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License version 2 for more details (a copy is
 * included at /legal/license.txt).
 * 
 * You should have received a copy of the GNU General Public License
 * version 2 along with this work; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA
 * 
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa
 * Clara, CA 95054 or visit www.sun.com if you need additional
 * information or have any questions.
 */

package com.sun.midp.lcdui;

import com.sun.midp.events.EventTypes;
import com.sun.midp.events.Event;

import com.sun.midp.log.Logging;
import com.sun.midp.log.LogChannels;

import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Item;

/**
 * An subclass for "synthetic" LCDUI events.  These events are generated by
 * LCDUI for its own purposes, and they include events whose type codes are
 * SCREEN_CHANGE_EVENT, CALL_SERIALLY_EVENT, INVALIDATE_EVENT and ITEM_EVENT.  
 * Events generated by the
 * system, such as keystrokes and commands, are not considered to be synthetic
 * events because they represent some actual user action.
 *
 * TBD: Class need to be PUIBLIC only 
 * if LCDUIEvent will be needed outside of the package
 */
class LCDUIEvent extends Event {
    /** Item for the item changed event. */
    Item changedItem = null;
    /** Next screen. */
    Displayable nextScreen = null;
    /** Target display of the event. */
    DisplayEventConsumer display;
    /** True if the display has be put in the foreground. */
    boolean hasForeground;

    /**
     * The event's minor code.  For events of type ITEM_EVENT, the minor code 
     * is either ITEM_STATE_CHANGED or ITEM_SIZE_REFRESH.
     */
    int minorCode;

    /** Constant specifying that an ITEM_EVENT indicates a state change. */
    static final int ITEM_STATE_CHANGED = 0;
    /** Constant specifying that an ITEM_EVENT requests a size refresh. */
    static final int ITEM_SIZE_REFRESH = 1;



    /**
     * Construct an event that has no parameters.
     *
     * @param type event ID type
     */
    private LCDUIEvent(int type) {
        super(type);
    }

    /**
     * Create a basic LCDUI event.
     * 
     * @param d Target Display
     * @param type event ID type
     *
     * @return initialized event
     */
    static LCDUIEvent createBasicEvent(DisplayEventConsumer d, int type) {
        LCDUIEvent event = new LCDUIEvent(type);

        if (Logging.TRACE_ENABLED) {
            if (d == null) {
                Logging.report(Logging.ERROR, LogChannels.LC_CORE,
                               "Event created with a null display target");
            }
        }

        event.display = d;
        return event;
    }

    /**
     * Create an item state changed event.
     *
     * @param src the Item src to the event
     * @param minor the minor code
     *
     * @return initialized event
     */
    static LCDUIEvent createItemEvent(Item src, int minor) {
        LCDUIEvent e = new LCDUIEvent(EventTypes.ITEM_EVENT);
        e.changedItem = src;
        e.minorCode = minor;
        return e;
    }

    /**
     * Create a screen repaint event.
     *
     * @param display parent Display of the Displayable
     * @return initialized event
     */
    static LCDUIEvent createScreenRepaintEvent(
        DisplayEventConsumer display) {
        LCDUIEvent e = new LCDUIEvent(EventTypes.SCREEN_REPAINT_EVENT);
        e.display = display;
        return e;
    }

    /**
     * Create a screen change event.
     *
     * @param parent parent Display of the Displayable
     * @param d The Displayable to change the current screen to
     *
     * @return initialized event
     */
    static LCDUIEvent createScreenChangeEvent(
        DisplayEventConsumer parent, 
        Displayable d) {
        LCDUIEvent e = new LCDUIEvent(EventTypes.SCREEN_CHANGE_EVENT);
        e.display = parent;
        e.nextScreen = d;
        return e;
    }
    
}

