# Solar Noon
## Background
12 o'clock your local time is usually not the exact middle of the day. (True) solar noon is the point when the sun is the highest in the sky, or alteratively, when the sun passes the meridian of the observer.

Solar noon varies throughout the year. These differences are caused by the the Earth's tilt and the Earth's elliptical orbit around the sun.

![figure 8 image](https://www.spaceweather.com/swpod2006/24dec06/tezel1.jpg)
*Composite photo of the sun at noon throughout the year, including a solar eclipse. Credit: Tunç Tezel.*

## How to Use
Run the program at [replit](https://replit.com/@lissajouslaser/solar-noon) by pressing the Run button.

Note that when entering the date, the program is lenient with inputs. Entering the 33rd of January will be interpreted as the 2nd of February.

The results can be checked against the values at [timeanddate](https://www.timeanddate.com/astronomy/australia/melbourne) by finding the meridian time.


## Implementation
The program will give the local time for solar noon, based on a date and a city location.

The program accounts for:
- The time zone of the location.
- The difference in longitude of the location from the central meridian of its time zone. The file *cityData.csv* is used to obtain a city's longitude.
- Daylight savings, using Java's ZoneRules class.
- The change in the time of solar noon throughout the year, through the [equation of time](https://en.wikipedia.org/wiki/Equation_of_time).

The program only works for cities that are in Java's ZoneId.getAvailableZoneIds() set, and with a population greater than one million. Melbourne, Sydney, Brisbane, Perth and Adelaide work. Captial cities meeting the population requirement should work.