package com.gregbucko.api.service.weather.resources.apis.health;

public class LivenessResourceApi {

    /**
     * By this point all resources should be accessible since Jersey should have initialized them therefore it is safe
     * to say that the application is live.
     *
     * @return Liveness status
     */
    public boolean get() {
        return true;
    }
}
