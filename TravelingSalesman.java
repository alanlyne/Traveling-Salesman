import java.io.*;
import java.util.*;

public class TravelingSalesman{

	public static ArrayList<University> unis = new ArrayList<>();

	public static void main(String[] args) throws IOException {
		Map<Integer, String> map = new HashMap<Integer, String>();

		BufferedReader br = new BufferedReader(new FileReader("Uni.txt"));
		String line = "";
		int i = 0;
		while ((line = br.readLine()) != null) {
			map.put(i, line);
			i++;
			String[] ar = line.split("	");
			int rank = Integer.parseInt(ar[0]);
			double lat = Double.parseDouble(ar[3]);
			double lon = Double.parseDouble(ar[4]);
			University uni = new University(rank, ar[1], ar[2], lat, lon);
			unis.add(uni);
		}

		int counter = 0;
		int start = 607;
		int secondPt = 0;
		double dist2 = 6750000;
		double totalDist = 0;
		boolean hitTop = false;
		while (counter < 1000) {   

			if (start == 0) {
				hitTop = false;
				totalDist += calc(start, start + 1);
				start++;
				counter++;
			} else if (start == 999) {
				hitTop = true;
				totalDist += calc(start, start - 1);
				start--;
				counter++;
			}

			else if (hitTop == false) {

				secondPt = start + 2;
				totalDist += calc(start, secondPt);
				start = secondPt;
				counter++;

			}

			else {
				secondPt = start - 2;
				totalDist += calc(start, secondPt);
				start = secondPt;
				// System.out.println("1:" + start);
				// System.out.println("2:" + secondPt);
				counter++;
			}
			System.out.print(start+ 1+",");
		}

		System.out.println(totalDist);
		// temp = calc(999, 998);
	}

	public static double calc(int loc1, int loc2) {

		int r = 6371; // Radius of Earth

		Double lat1 = unis.get(loc1).getLat();
		Double lon1 = unis.get(loc1).getLon();
		Double lat2 = unis.get(loc2).getLat();
		Double lon2 = unis.get(loc2).getLon();

		Double latD = rad(lat2 - lat1);
		Double lonD = rad(lon2 - lon1);

		Double x = Math.sin(latD / 2) * Math.sin(latD / 2)
				+ Math.cos(rad(lat1)) * Math.cos(rad(lat2)) * Math.sin(lonD / 2) * Math.sin(lonD / 2);
		Double y = 2 * Math.atan2(Math.sqrt(x), Math.sqrt(1 - x));
		Double distance = r * y;

		return distance;
	}

	private static Double rad(Double value) {

		return value * Math.PI / 180;

	}

}

class University {
	private int id;
	private String uniName;
	private String country;
	private double lon;
	private double lat;

	University(int id, String uniName, String country, double lat, double lon) {
		this.id = id;
		this.uniName = uniName;
		this.country = country;
		this.lon = lon;
		this.lat = lat;
	}

	public int getId() {
		return id;
	}

	public String getUniName() {
		return uniName;
	}

	public String getCountry() {
		return country;
	}

	public double getLon() {
		return lon;
	}

	public double getLat() {
		return lat;
	}
}