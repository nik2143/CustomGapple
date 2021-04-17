package io.github.nik2143.customgapple;

public class Configuration {

    public int defaultAmount;
    public int defaultDuration;
    public boolean applyonlybetter;

    public Configuration(){
        loadConfig();
    }

    public void loadConfig(){
        defaultAmount = CustomGapple.getCustomGapple().getConfig().getInt("Default-Amount");
        defaultDuration = CustomGapple.getCustomGapple().getConfig().getInt("Default-Duration");
        applyonlybetter = CustomGapple.getCustomGapple().getConfig().getBoolean("apply-only-better",false);
    }

}
