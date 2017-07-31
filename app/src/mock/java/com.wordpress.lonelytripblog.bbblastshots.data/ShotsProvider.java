package com.wordpress.lonelytripblog.bbblastshots.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Dummy provider for testing. Provides the same objects all the time.
 */

public class ShotsProvider implements ProviderInterface {

    private List<Shot> shots;

    public ShotsProvider() {
        shots = new ArrayList<>(Arrays.asList(
                new Shot(3697212, "Day 10 #ThirtyUI - Drive-Thru Touchscreen",
                    "<p>Drive-Thru Touchscreen for McDonalds with the start screen + the single item screen.</p>\n\n<p><a href=\"http://andreasvdesigns.com/\" rel=\"nofollow noreferrer\">Website</a>\n<br /><a href=\"https://www.behance.net/andreasvdesigns\" rel=\"nofollow noreferrer\">Behance</a>\n<br /><a href=\"https://twitter.com/AndreasVDesigns\" rel=\"nofollow noreferrer\">Twitter</a>\n<br /><a href=\"https://www.instagram.com/andreasvdesigns/\" rel=\"nofollow noreferrer\">Instagram</a></p>",
                    "https://cdn.dribbble.com/users/1422274/screenshots/3697212/dribbble.png",
                    "https://cdn.dribbble.com/users/1422274/screenshots/3697212/dribbble_1x.png",
                    "https://cdn.dribbble.com/users/1422274/screenshots/3697212/dribbble_teaser.png"),
                new Shot(3695941,
                    "Noka Studio Final Logo",
                    "<p>Hey guys,</p>\n\n<p>So we wanted to basically show you guys our full logo that was developed across probably 6 - 8 weeks.</p>\n\n<p>As anyone knows self-branding can be one of the most tedious things on the planet so not only did we switch names (such as arkade) a number of times, but also styles, forms etc. </p>\n\n<p>The final logo, noka, was based on a number of things. First its a combination of our two names. We combined capitals and lower case letters because were quite indecisive in real life and love messing around. Whilst the elongated 'o' represents our vision, growing family and want to truly be a holistic design studio.</p>\n\n<p>It means something to us and hopefully you guys like the logo and some that came before it, attached.</p>\n\n<p>Have a great weekend!</p>",
                    null,
                    "https://cdn.dribbble.com/users/1825138/screenshots/3695941/noka_logo_final_alt_1x.png",
                    "https://cdn.dribbble.com/users/1825138/screenshots/3695941/noka_logo_final_alt_teaser.png"),
                new Shot(3695940,
                    "Pirate Sub",
                    "<p>Day 342/365</p>",
                    null, null,
                    "https://cdn.dribbble.com/users/1008875/screenshots/3695940/pirate-sub_teaser.png")
        ));
    }


    @Override
    public void getShots(LoadShotsCallback callback) {
        callback.onShotsLoaded(shots);
    }

    @Override
    public void getNewShots(LoadShotsCallback callback) {
        callback.onShotsLoaded(shots);
    }

    public List<Shot> getShots() {
        return shots;
    }

    public void setShots(List<Shot> shots) {
        this.shots = shots;
    }
}
