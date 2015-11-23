package org.algohub.rest.suite;


import org.algohub.rest.integration.controller.AuthenticationControllerTest;
import org.algohub.rest.integration.controller.ProtectedControllerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    AuthenticationControllerTest.class,
    ProtectedControllerTest.class
})
public class IntegrationTestSuite {

}
