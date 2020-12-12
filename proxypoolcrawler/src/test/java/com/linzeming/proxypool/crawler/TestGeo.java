package com.linzeming.proxypool.crawler;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.WebServiceClient;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.model.CountryResponse;
import com.maxmind.geoip2.record.*;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class TestGeo {


    @Test
    public void givenIP_whenFetchingCity_thenReturnsCityData() {
// This creates a WebServiceClient object that is thread-safe and can be
// reused across requests. Reusing the object will allow it to keep
// connections alive for future requests. The object is closeable, but
// it should not be closed until you are finished making requests with it.
//
// Replace "42" with your account ID and "license_key" with your license key.
// To use the GeoLite2 web service instead of GeoIP2 Precision, call the
// host method on the builder with "geolite.info", e.g.
// new WebServiceClient.Builder(42, "license_key").host("geolite.info").build()
        try (WebServiceClient client = new WebServiceClient.Builder(42, "license_key")
                .build()) {

            InetAddress ipAddress = InetAddress.getByName("128.101.101.101");

            // Do the lookup
            CountryResponse response = client.country(ipAddress);

            Country country = response.getCountry();
            System.out.println(country.getIsoCode());            // 'US'
            System.out.println(country.getName());               // 'United States'
            System.out.println(country.getNames().get("zh-CN")); // '美国'
        } catch (IOException | GeoIp2Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDatabaseGeo() throws IOException, GeoIp2Exception {
        // A File object pointing to your GeoIP2 or GeoLite2 database
        File database = new File("C:\\Users\\linze\\Desktop\\GeoLite2-City_20201208.tar\\GeoLite2-City_20201208\\GeoLite2-City.mmdb");
// This creates the DatabaseReader object. To improve performance, reuse
// the object across lookups. The object is thread-safe.
        DatabaseReader reader = new DatabaseReader.Builder(database).build();

        InetAddress ipAddress = InetAddress.getByName("182.52.90.117");

// Replace "city" with the appropriate method for your database, e.g.,
// "country".
        CityResponse response = reader.city(ipAddress);

        Country country = response.getCountry();
        System.out.println(country.getIsoCode());            // 'US'
        System.out.println(country.getName());               // 'United States'
        System.out.println(country.getNames().get("zh-CN")); // '美国'

        Subdivision subdivision = response.getMostSpecificSubdivision();
        System.out.println(subdivision.getName());    // 'Minnesota'
        System.out.println(subdivision.getIsoCode()); // 'MN'

        City city = response.getCity();
        System.out.println(city.getName()); // 'Minneapolis'
        System.out.println(city.getNames().get("zh-CN")); // 'Minneapolis'

        Postal postal = response.getPostal();
        System.out.println(postal.getCode()); // '55455'

        Location location = response.getLocation();
        System.out.println(location.getLatitude());  // 44.9733
        System.out.println(location.getLongitude()); // -93.2323
    }
}
