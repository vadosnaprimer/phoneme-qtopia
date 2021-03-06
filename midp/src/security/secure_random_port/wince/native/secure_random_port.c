/*
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
#include <stddef.h>
#include <java_types.h>
#include <secure_random_port.h>
#include <Winbase.h>
#include <kni.h>

jboolean get_random_bytes_port(unsigned char*buffer, jint bufsize) {

  /* DO NOT USE THIS TIME-BASED CODE FOR END USER PRODUCTS !!! */

  /* IMPL_NOTE:
   * The problem this function must solve is to obtain a set of really
   * unpredictable bits for use in cryptography.
   * There is no portable solution to this problem.
   *
   * The current time-based implementation generates a
   * cryptographically weak seed and MUST NOT be used in end-user's devices.
   * For real-world use, it MUST be replaced by something else 
   * by the porting engineers, and the replacement code will be 
   * platform-specific.
   *
   * Current time MUST NOT be used for seed generation because time is
   * predictable with a good precision, the accuracy of measurement
   * is limited (for example, a function that returns time in microseconds
   * may be just multiplying tenths of second by 100000), which makes
   * the number of really unpredictable bits small.
   *
   * System configuration parameters also are not a suitable source
   * of randomness because they may be learned from real-world sources
   * or obtained by an installed MIDlet.
   * External events, such as network packet arrival times, can
   * also be manipulated by adversary.
   *
   * (see IETF RFC 1750, Randomness Recommendations for Security,
   *  http://www.ietf.org/rfc/rfc1750.txt)
   */

    SYSTEMTIME st;
    jlong res;
    int i;
    GetSystemTime(&st);
    res = (jlong)(st.wMilliseconds + st.wSecond * 1000);
    for(i=0; i<bufsize; i++) {
        buffer[i] = (unsigned char)res;
        res >>= 8;
    }
    return KNI_TRUE;
}


