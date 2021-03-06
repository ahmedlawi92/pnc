/**
 * JBoss, Home of Professional Open Source.
 * Copyright 2014 Red Hat, Inc., and individual contributors
 * as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.pnc.spi.builddriver;

import org.jboss.pnc.model.BuildStatus;

/**
 * List of Jenkins job statuses.
 *
 * Created by <a href="mailto:matejonnet@gmail.com">Matej Lazar</a> on 2014-11-23.
 */
public enum BuildDriverStatus {
    SUCCESS, FAILED, UNSTABLE, BUILDING, ABORTED, CANCELLED, UNKNOWN;

    /**
     * Converts BuildDriverStatus to BuildStatus
     * 
     * @return Corresponding BuildStatus
     */
    public BuildStatus toBuildStatus() {
        switch (this) {
            case SUCCESS:
                return BuildStatus.SUCCESS;
            case FAILED:
                return BuildStatus.FAILED;
            case UNSTABLE:
                return BuildStatus.UNSTABLE;
            case BUILDING:
                return BuildStatus.BUILDING;
            case ABORTED:
                return BuildStatus.ABORTED;
            case CANCELLED:
                return BuildStatus.CANCELLED;
            case UNKNOWN:
                return BuildStatus.UNKNOWN;
            default:
                throw new IllegalStateException("Bad design of BuildDriverStatus enum type");

        }
    }
}
