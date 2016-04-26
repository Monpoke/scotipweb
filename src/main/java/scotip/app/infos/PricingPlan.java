package scotip.app.infos;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pierre on 23/04/2016.
 */
public class PricingPlan {

    private String name;

    // month
    private double value;

    private boolean bestPlan = false;

    private final List<String> advantages;
    private String link = "Purchase";


    /**
     * Create a pricing plan.
     *
     * @param name
     * @param value
     */
    public PricingPlan(String name, double value) {
        this.name = name;
        this.value = value;
        advantages = new ArrayList<>();
    }


    public void setLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void addAdvantage(String adv) {
        advantages.add(adv);
    }

    public boolean isBestPlan() {
        return bestPlan;
    }

    public void setBestPlan(boolean bestPlan) {
        this.bestPlan = bestPlan;
    }

    public List<String> getAdvantages() {
        return advantages;
    }


    /*
    ====================================================
    CURRENT PLANS
    ====================================================
     */
    public static List<PricingPlan> getCurrentPlans() {
        ArrayList<PricingPlan> pricingPlan = new ArrayList<>();

        PricingPlan cur = new PricingPlan("Free", 0);
        cur.addAdvantage("2x <span>services</span>");
        cur.addAdvantage("2x <span>SIP users</span>");
        cur.addAdvantage("<span class='no'>Premium call history</span>");
        cur.addAdvantage("<span class='no'>No dedicated number</span>");
        cur.setLink("Try it now!");
        pricingPlan.add(cur);

        cur = new PricingPlan("Silver", 9.99);
        cur.addAdvantage("5x <span>services</span>");
        cur.addAdvantage("10x <span>SIP users</span>");
        cur.addAdvantage("5x <span>SIP channels</span>");
        cur.addAdvantage("Premium call history <span>included</span>");
        cur.addAdvantage("<span class='no'>No dedicated number</span>");
        cur.setBestPlan(true);
        pricingPlan.add(cur);

        cur = new PricingPlan("Golden", 49.99);
        cur.addAdvantage("20x <span>services</span>");
        cur.addAdvantage("50x <span>SIP users</span>");
        cur.addAdvantage("50x <span>SIP channels</span>");
        cur.addAdvantage("Premium call history <span>included</span>");
        cur.addAdvantage("1x <span>dedicated number</span>");
        pricingPlan.add(cur);

        cur = new PricingPlan("Custom", -1);
        cur.addAdvantage("More <span>services</span>?");
        cur.addAdvantage("More <span>SIP users</span>?");
        cur.addAdvantage("More <span>SIP chan</span>?");
        cur.addAdvantage("Mode <span>dedicated number</span>?");
        cur.addAdvantage("And more?");
        cur.setLink("Contact-us!");
        pricingPlan.add(cur);


        return pricingPlan;
    }

}

