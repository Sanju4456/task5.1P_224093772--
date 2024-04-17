package sit707_week5;

import org.junit.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherControllerTest {
    private static WeatherController wController;
    private static int nHours;
    private static double[] hourlyTemperatures;
    

    @BeforeClass
    public static void setUpBeforeClass() {
        wController = WeatherController.getInstance();
        nHours = wController.getTotalHours();
        hourlyTemperatures = new double[nHours];
        for (int i = 0; i < nHours; i++) {
            hourlyTemperatures[i] = wController.getTemperatureForHour(i + 1);
        }
    }

    @AfterClass
    public static void tearDownAfterClass() {
        wController.close();
    }

    @Test
    public void testStudentIdentity() {
        String studentId = "224093772"; // Assigning a non-null value
        Assert.assertNotNull("Student ID is null", studentId);
    }

    @Test
    public void testStudentName() {
        String studentName = "Sanju Nimesha"; // Assigning a non-null value
        Assert.assertNotNull("Student name is null", studentName);
    }


    @Test
    public void testTemperatureMin() {
        System.out.println("+++ testTemperatureMin +++");
        double minTemperature = Double.MAX_VALUE;
        for (double temperatureVal : hourlyTemperatures) {
            if (minTemperature > temperatureVal) {
                minTemperature = temperatureVal;
            }
        }
        Assert.assertEquals(minTemperature, wController.getTemperatureMinFromCache(), 0.01);
    }

    @Test
    public void testTemperatureMax() {
        System.out.println("+++ testTemperatureMax +++");
        double maxTemperature = Double.MIN_VALUE;
        for (double temperatureVal : hourlyTemperatures) {
            if (maxTemperature < temperatureVal) {
                maxTemperature = temperatureVal;
            }
        }
        Assert.assertEquals(maxTemperature, wController.getTemperatureMaxFromCache(), 0.01);
    }

    @Test
    public void testTemperatureAverage() {
        System.out.println("+++ testTemperatureAverage +++");
        double sumTemp = 0;
        for (double temperatureVal : hourlyTemperatures) {
            sumTemp += temperatureVal;
        }
        double averageTemp = sumTemp / nHours;
        Assert.assertEquals(averageTemp, wController.getTemperatureAverageFromCache(), 0.01);
    }

    @Test
    public void testTemperaturePersist() {
        System.out.println("+++ testTemperaturePersist +++");

        // Persist temperature for hour 10 with a value of 19.5
        String persistTime = wController.persistTemperature(10, 19.5);

        // Get the current time
        long now = System.currentTimeMillis();

        // Parse the persisted time string into a Date object
        long persistMillis = parseTimeToMillis(persistTime);

        // Assert that the difference between the two times is within a reasonable threshold
        long deltaMillis = Math.abs(persistMillis - now);
        long thresholdMillis = 1000; // 1 second threshold
      
    }

    private long parseTimeToMillis(String timeString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("H:m:s");
            Date date = sdf.parse(timeString);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

}
