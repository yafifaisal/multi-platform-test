package web.helpers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomDataGenerator {

    private static final Random random = new Random();

    private static final List<String> GENDERS = Arrays.asList("male", "female");
    private static final List<String> WEEKDAYS = Arrays.asList("sunday", "monday", "tuesday", "wednesday", "thursday",
            "friday", "saturday");
    private static final List<String> COLORS = Arrays.asList("red", "blue", "green", "yellow", "white");
    private static final List<String> ANIMALS = Arrays.asList("cat", "cheetah", "dog", "deer", "elephant", "fox",
            "giraffe", "lion", "rabbit");
    private static final List<String> COUNTRIES = Arrays.asList("usa", "canada", "uk", "germany", "france", "australia",
            "japan", "china", "brazil", "india");

    private static final DateTimeFormatter US_DATE_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private static final DateTimeFormatter EU_DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static String getRandomGender() {
        return getRandomFromList(GENDERS);
    }

    public static List<String> getRandomWeekdays(int count) {
        Collections.shuffle(WEEKDAYS);
        return WEEKDAYS.subList(0, Math.min(count, WEEKDAYS.size()));
    }

    public static String getRandomColor() {
        return getRandomFromList(COLORS);
    }

    public static String getRandomAnimal() {
        return getRandomFromList(ANIMALS);
    }

    public static String getRandomCountry() {
        return getRandomFromList(COUNTRIES);
    }

    public static String getRandomUSStartDate() {
        LocalDate startDate = LocalDate.now().plusDays(random.nextInt(5));
        return startDate.format(US_DATE_FORMAT);
    }

    public static String getRandomUSEndDate(LocalDate date1) {
        LocalDate date2 = date1.plusDays(1 + random.nextInt(5));
        return date2.format(US_DATE_FORMAT);
    }

    public static LocalDate[] getDateRange() {
        LocalDate start = LocalDate.now().plusDays(random.nextInt(5));
        LocalDate end = start.plusDays(1 + random.nextInt(10));
        return new LocalDate[] { start, end };
    }

    public static String formatToEu(LocalDate date) {
        return date.format(EU_DATE_FORMAT);
    }

    public static String getExpectedDateRangeMessage(LocalDate start, LocalDate end) {
        long days = end.toEpochDay() - start.toEpochDay();
        return "You selected a range of " + days + " days.";
    }

    private static String getRandomFromList(List<String> list) {
        return list.get(random.nextInt(list.size()));
    }

    public static Map<String, String> getRandomDatePair() {
        LocalDate startDate = LocalDate.now().plusDays(ThreadLocalRandom.current().nextInt(1, 5)); // today + 1~4 days
        LocalDate endDate = startDate.plusDays(ThreadLocalRandom.current().nextInt(1, 5)); // +1~4 days after start

        DateTimeFormatter mmddyyyy = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        DateTimeFormatter ddmmyyyy = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        Map<String, String> result = new HashMap<>();
        result.put("date1_mmddyyyy", startDate.format(mmddyyyy));
        result.put("date2_mmddyyyy", endDate.format(mmddyyyy));
        result.put("date1_ddmmyyyy", startDate.format(ddmmyyyy));
        result.put("date2_ddmmyyyy", endDate.format(ddmmyyyy));
        result.put("range_days", String.valueOf(ChronoUnit.DAYS.between(startDate, endDate)));

        return result;
    }

}
