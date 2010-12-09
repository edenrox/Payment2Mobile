/***************************************
 * DO NOT REMOVE
 * License: BSD License (see LICENSE in the root of this project)
 * Original Author: Ian Hopkins
 */
package org.rhok.payout2mobile.model;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

public final class PMF {
    private static final PersistenceManagerFactory pmfInstance =
        JDOHelper.getPersistenceManagerFactory("transactions-optional");

    private PMF() {}

    public static PersistenceManagerFactory get() {
        return pmfInstance;
    }
}