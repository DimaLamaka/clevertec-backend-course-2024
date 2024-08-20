package ru.clevertec.unit.testing.step2.annotations.assertsandother;

import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

//run with gradle gradle --tests "ru.clevertec.unit.testing.step2.annotations.assertsandother.SuiteTest"
@Suite(failIfNoTests = false)
@IncludeTags("tag")
@SelectPackages("ru.clevertec.unit.testing")
public class SuiteTest {
}
