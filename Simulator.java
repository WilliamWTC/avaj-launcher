import java.util.*;
import java.io.*;

public class Simulator {

    private static WeatherTower weatherTower;
    private static List<Flyable> flyables = new ArrayList<Flyable>();

    public static void main(String[] arg) throws InterruptedException {

        try
        {
            //weatherTower.wpenFile();
            BufferedReader buff = new BufferedReader(new FileReader(arg[0]));
            String line = buff.readLine();
            line = line.trim();
            if (line != null)
            {
                weatherTower = new WeatherTower();
                int simulation = Integer.parseInt(line.split(" ")[0]);
                if (simulation < 0)
                {
                    System.out.println("Invalid simulation count" + simulation);
                    System.exit(1);
                }
                while ((line = buff.readLine()) != null)
                {
                    line = line.trim();
                    if (line != null && line.split(" ").length == 5) {
                        Flyable flyable = AircraftFactory.newAircraft(line.split(" ")[0], line.split(" ")[1],
                                Integer.parseInt(line.split(" ")[2]), Integer.parseInt(line.split(" ")[3]),
                                Integer.parseInt(line.split(" ")[4]));
                        flyables.add(flyable);
                    }
                }

                for (Flyable flyable : flyables) {
                    flyable.registerTower(weatherTower);
                }
                for (int i = 1; i <= simulation; i++) {
                    weatherTower.writeToFile("\n" + "simulation: " + i +"\n");
                    weatherTower.changeWeather();
                }
            }
            buff.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found" + arg[0]);
        }
        catch (IOException e)
        {
            System.out.println("error while reading the file");
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            System.out.println("No file specified");
        }
    }
}