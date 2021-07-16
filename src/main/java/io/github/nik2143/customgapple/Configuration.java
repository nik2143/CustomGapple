package io.github.nik2143.customgapple;

import io.github.nik2143.customgapple.utils.Utils;

public class Configuration {

    public int defaultAmount;
    public int defaultDuration;
    public boolean applyonlybetter;
    public String nopermsmsg;

    public Configuration(){
        loadConfig();
    }

    private void loadConfig(){
        defaultAmount = CustomGapple.getCustomGapple().getConfig().getInt("Default-Amount");
        defaultDuration = CustomGapple.getCustomGapple().getConfig().getInt("Default-Duration");
        applyonlybetter = CustomGapple.getCustomGapple().getConfig().getBoolean("apply-only-better",false);
        nopermsmsg = Utils.color(CustomGapple.getCustomGapple().getConfig().getString("nopermsmsg",null));
    }

}
