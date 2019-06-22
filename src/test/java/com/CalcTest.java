package com;

import org.testng.Assert;
import org.testng.annotations.*;
import sun.util.locale.LocaleUtils;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

import static org.testng.Assert.assertEquals;

public class CalcTest {

    private Calc calc = new Calc();

    @BeforeMethod
    public void setUp() throws Exception {
        System.out.println("@BeforeMethod");
    }

    @AfterMethod
    public void tearDown() throws Exception {
        System.out.println("@AfterMethod");
    }

    @BeforeGroups
    public void beforeGroups() throws Exception {
        System.out.println("BeforeGroups");
    }

    @AfterGroups
    public void afterGroups() throws Exception {
        System.out.println("@AfterGroups");
    }

    @BeforeClass
    public void beforeClass() throws Exception {
        System.out.println("@BeforeClass");
    }

    @BeforeTest
    public void beforeTest() throws Exception {
        System.out.println("@BeforeTest");
    }

    @AfterTest
    public void afterTest() throws Exception {
        System.out.println("@AfterTest");
    }

    @AfterClass
    public void afterClass() throws Exception {
        System.out.println("@AfterClass");
    }

    @BeforeSuite
    public void beforeSuite() throws Exception {
        System.out.println("@BeforeSuite");
    }

    @AfterSuite
    public void afterSuite() throws Exception {
        System.out.println("@AfterSuite");
    }

    @Test
    public void testSum() throws Exception {
        assertEquals(5, calc.sum(2,3));
        System.out.println("@Test");
    }

    @Test(enabled = false)
    public void testsetProperty() {
        System.out.println("Этот метод будет проигнорирован в момент проведения тестирования!");
    }

    @Test(timeOut = 1000)
    public void waitLongTime() throws Exception {
        Thread.sleep(1001);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testNullPointerException() {
        List list = null;
        int size = list.size();
    }

    @Test(expectedExceptionsMessageRegExp = "^For input string: \"(.*)\"$")
    public void testSomeRegExpMessageOfException() {
        throw new NumberFormatException();
    }

    //groups
    @Test(groups={"unit1","integration"})
    public void testingMethod1() {
        System.out.println("testingMethod1");
    }

    @Test(groups={"unit2","integration"})
    public void testingMethod2() {
        System.out.println("testingMethod2");
    }

    @Test(groups={"unit1"})
    public void testingMethod3() {
        System.out.println("testingMethod3");
    }

    @Test(groups={"unit1", "unit2"})
    public void testingMethod4() {
        System.out.println("testingMethod4");
    }
    //groups end

    //dependsOn
    @Test
    public void initEnvironmentTest() {
        System.out.println("This is initEnvironmentTest");
    }

    @Test(dependsOnMethods={"initEnvironmentTest"})
    public void testmethod() {
        System.out.println("This is testmethod");
    }
    //dependsOn end

    //dependsOnGroups
    @Test(groups = { "init" })
    public void initEnvironmentTest1G() {
        System.out.println("This is initEnvironmentTest1");
    }

    @Test(groups = { "init" })
    public void initEnvironmentTest2G() {
        System.out.println("This is initEnvironmentTest2");
    }

    @Test(dependsOnGroups={"init"})
    public void testmethodG() {
        System.out.println("This is testmethod");
    }
    //dependsOnGroups end

    @Test(description = "some destription of a test")
    public void description() {
        System.out.println("This is description");
    }

    @Test(priority = 1)
    public void priority1() {
        System.out.println("priority 1");
    }

    @Test(priority = 2)
    public void priority2() {
        System.out.println("priority 2");
    }

    //dataProvider
    @DataProvider
    public Object[][] someDataProvider() {
        return new Object[][]{
                {null, null},
                {"", "xcvzxv"},
                {new Object(), 1245L},
        };
    }

    @Test(dataProvider = "someDataProvider")
    public void testParseLocale(Object actual, Object expected) {
        System.out.println(actual + " " + expected);
    }
    //dataProvider end

    //parameters
    private DataSource dataSource;

    /**
     * parameters Param1 and Param2 have to in the file
     * K:\IntelliJ IDEA Community Edition 2018.2 FREE\customsettings\.IdeaIC\system\temp-testng-customsuite.xml
     * like that
     * <?xml version="1.0" encoding="UTF-8"?>
     * <!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd">
     * <suite name="Default Suite">
     *   <test name="TestNG">
     * 	<parameter name="Param1" value="Value_Of_Param1"/>
     * 	<parameter name="Param2" value="Value_Of_Param2"/>
     *     <classes>
     *       <class name="com.CalcTest">
     *         <methods>
     *           <include name="testOptionalData"/>
     *         </methods>
     *       </class> <!-- com.CalcTest -->
     *     </classes>
     *   </test> <!-- TestNG -->
     * </suite> <!-- Default Suite -->
     */
    @Parameters({"Param1", "Param2", "username", "password"})
    @BeforeClass
    public void setUpDataSource(String driver, String url, @Optional("sa") String username, @Optional String password) {
        // create datasource
        dataSource = null;
    }

    @Test
    public void testOptionalData() throws SQLException {
        dataSource.getConnection();
        // do some staff
    }
    //parameters end

    //factory of dataSrources and parameters
    @DataProvider
    public Object[][] tablesData() {
        return new Object[][] {
                {"FIRST_TABLE"},
                {"SECOND_TABLE"},
                {"THIRD_TABLE"},
        };
    }

    @Factory(dataProvider = "tablesData")
    public Object[] createTest(String table) {
        return new Object[] { new GenericTableTest(table) };
    }

    @Parameters("table")
    @Factory
    public Object[] createParameterizedTest(@Optional("SOME_TABLE") String table) {
        return new Object[] { new GenericTableTest(table) };
    }

    public class GenericTableTest extends Assert {
        private final String table;

        public GenericTableTest(final String table) {
            this.table = table;
        }

        @Test
        public void testTable() {
            System.out.println(table);
            // do some testing staff here
        }
        //factory of dataSrources and parameters end
    }
}
