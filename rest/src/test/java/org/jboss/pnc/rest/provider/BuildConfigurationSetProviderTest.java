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
package org.jboss.pnc.rest.provider;

import org.jboss.pnc.model.BuildConfiguration;
import org.jboss.pnc.model.BuildConfigurationSet;
import org.jboss.pnc.spi.datastore.repositories.BuildConfigurationRepository;
import org.jboss.pnc.spi.datastore.repositories.BuildConfigurationSetRepository;
import org.jboss.pnc.spi.datastore.repositories.BuildRecordRepository;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class BuildConfigurationSetProviderTest {

    @Test
    public void shouldThrowConflictedEntryExceptionWhenAddingDuplicatedConfiguration() {
        //given
        BuildConfigurationSetRepository buildConfigurationSetRepository = mock(BuildConfigurationSetRepository.class);
        BuildConfigurationRepository buildConfigurationRepository = mock(BuildConfigurationRepository.class);
        BuildRecordRepository buildRecordRepository = mock(BuildRecordRepository.class);

        BuildConfigurationSet testBCS = createBuildConfigurationSet(1);
        when(buildConfigurationSetRepository.queryById(1)).thenReturn(testBCS);
        when(buildConfigurationRepository.queryById(2)).thenReturn(createBuildConfiguration(2));

        BuildConfigurationSetProvider buildConfigurationSetProvider = new BuildConfigurationSetProvider(buildConfigurationSetRepository, buildConfigurationRepository, buildRecordRepository, null, null, null);

        //when
        addConfigsToSet(testBCS, createBuildConfiguration(1), createBuildConfiguration(2));
        try {
            buildConfigurationSetProvider.addConfiguration(1, 2);
            fail();
        } catch (ConflictedEntryException ignoreMe) {
            //then
        }
    }

    @Test
    public void shouldAddNewBuildConfigurationToSet() throws Exception {
        BuildConfigurationSetRepository buildConfigurationSetRepository = mock(BuildConfigurationSetRepository.class);
        BuildConfigurationRepository buildConfigurationRepository = mock(BuildConfigurationRepository.class);
        BuildRecordRepository buildRecordRepository = mock(BuildRecordRepository.class);

        BuildConfigurationSet testBCS = createBuildConfigurationSet(1);

        when(buildConfigurationSetRepository.queryById(1)).thenReturn(testBCS);
        when(buildConfigurationRepository.queryById(3)).thenReturn(createBuildConfiguration(3));

        BuildConfigurationSetProvider buildConfigurationSetProvider = new BuildConfigurationSetProvider(buildConfigurationSetRepository, buildConfigurationRepository, buildRecordRepository, null, null, null);

        //when
        addConfigsToSet(testBCS, createBuildConfiguration(1), createBuildConfiguration(2));
        buildConfigurationSetProvider.addConfiguration(1,3);

        //then
        assertThat(testBCS.getBuildConfigurations()).hasSize(3);
    }

    private BuildConfiguration createBuildConfiguration(int id){
        BuildConfiguration retVal = new BuildConfiguration();
        retVal.setId(id);
        return retVal;
    }

    private BuildConfigurationSet createBuildConfigurationSet(int id){
        BuildConfigurationSet retVal = new BuildConfigurationSet();
        retVal.setId(id);
        return retVal;
    }

    private void addConfigsToSet(BuildConfigurationSet bset, BuildConfiguration... bconfigs){
        for (BuildConfiguration config : bconfigs){
            bset.addBuildConfiguration(config);
        }
    }

}
