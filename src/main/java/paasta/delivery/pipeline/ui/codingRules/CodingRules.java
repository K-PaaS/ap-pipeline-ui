package paasta.delivery.pipeline.ui.codingRules;

import java.util.List;
import java.util.Map;

/**
 * The type Coding rules.
 */
public class CodingRules {

    private String key;
    private String qprofile;
    private int ps = 1;
    private int p = 1;
    private String f;
    private Object facets;
    private String activation;
    private int total;
    private String custom_key;
    private String markdown_description;
    private String name;
    private String language;
    private Object actives;
    private String resultStatus;
    private String resultMessage;
    private String languages;
    private String s;
    private String asc;
    private String severities;
    private List rules;
    private CodingRules condition;
    private String q;
    private Map rule;
    private String serviceInstanceId;
    // custom
    private List activatedQProfiles;

    // --[getter/setter]-----------------------------------

    /**
     * Gets total.
     *
     * @return the total
     */
    public int getTotal() {
        return total;
    }

    /**
     * Sets total.
     *
     * @param total the total
     */
    public void setTotal(int total) {
        this.total = total;
    }


    /**
     * Sets key.
     *
     * @param key the key
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Gets key.
     *
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * Gets qprofile.
     *
     * @return the qprofile
     */
    public String getQprofile() {
        return qprofile;
    }

    /**
     * Sets qprofile.
     *
     * @param qprofile the qprofile
     */
    public void setQprofile(String qprofile) {
        this.qprofile = qprofile;
    }

    /**
     * Sets ps.
     *
     * @param ps the ps
     */
    public void setPs(int ps) {
        this.ps = ps;
    }

    /**
     * Gets facets.
     *
     * @return the facets
     */
    public Object getFacets() {
        return facets;
    }

    /**
     * Sets facets.
     *
     * @param facets the facets
     */
    public void setFacets(Object facets) {
        this.facets = facets;
    }

    /**
     * Gets activation.
     *
     * @return the activation
     */
    public String getActivation() {
        return activation;
    }

    /**
     * Sets activation.
     *
     * @param activation the activation
     */
    public void setActivation(String activation) {
        this.activation = activation;
    }

    /**
     * Gets ps.
     *
     * @return the ps
     */
    public int getPs() {
        return ps;
    }

    /**
     * Gets f.
     *
     * @return the f
     */
    public String getF() {
        return f;
    }

    /**
     * Sets f.
     *
     * @param f the f
     */
    public void setF(String f) {
        this.f = f;
    }

    /**
     * Gets p.
     *
     * @return the p
     */
    public int getP() {
        return p;
    }

    /**
     * Gets result status.
     *
     * @return the result status
     */
    public String getResultStatus() {
        return resultStatus;
    }

    /**
     * Sets result status.
     *
     * @param resultStatus the result status
     */
    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }

    /**
     * Gets result message.
     *
     * @return the result message
     */
    public String getResultMessage() {
        return resultMessage;
    }

    /**
     * Sets result message.
     *
     * @param resultMessage the result message
     */
    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    /**
     * Gets custom key.
     *
     * @return the custom key
     */
    public String getCustom_key() {
        return custom_key;
    }

    /**
     * Sets custom key.
     *
     * @param custom_key the custom key
     */
    public void setCustom_key(String custom_key) {
        this.custom_key = custom_key;
    }

    /**
     * Gets markdown description.
     *
     * @return the markdown description
     */
    public String getMarkdown_description() {
        return markdown_description;
    }

    /**
     * Sets markdown description.
     *
     * @param markdown_description the markdown description
     */
    public void setMarkdown_description(String markdown_description) {
        this.markdown_description = markdown_description;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets language.
     *
     * @return the language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Sets language.
     *
     * @param language the language
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Sets p.
     *
     * @param p the p
     */
    public void setP(int p) {
        this.p = p;
    }

    /**
     * Gets s.
     *
     * @return the s
     */
    public String getS() {
        return s;
    }

    /**
     * Sets s.
     *
     * @param s the s
     */
    public void setS(String s) {
        this.s = s;
    }


    /**
     * Gets languages.
     *
     * @return the languages
     */
    public String getLanguages() {
        return languages;
    }

    /**
     * Sets languages.
     *
     * @param languages the languages
     */
    public void setLanguages(String languages) {
        this.languages = languages;
    }

    /**
     * Gets asc.
     *
     * @return the asc
     */
    public String getAsc() {
        return asc;
    }

    /**
     * Sets asc.
     *
     * @param asc the asc
     */
    public void setAsc(String asc) {
        this.asc = asc;
    }

    /**
     * Gets rules.
     *
     * @return the rules
     */
    public List getRules() {
        return rules;
    }

    /**
     * Sets rules.
     *
     * @param rules the rules
     */
    public void setRules(List rules) {
        this.rules = rules;
    }

    /**
     * Gets condition.
     *
     * @return the condition
     */
    public CodingRules getCondition() {
        return condition;
    }

    /**
     * Sets condition.
     *
     * @param condition the condition
     */
    public void setCondition(CodingRules condition) {
        this.condition = condition;
    }

    /**
     * Gets q.
     *
     * @return the q
     */
    public String getQ() {
        return q;
    }

    /**
     * Sets q.
     *
     * @param q the q
     */
    public void setQ(String q) {
        this.q = q;
    }

    /**
     * Gets severities.
     *
     * @return the severities
     */
    public String getSeverities() {
        return severities;
    }

    /**
     * Sets severities.
     *
     * @param severities the severities
     */
    public void setSeverities(String severities) {
        this.severities = severities;
    }

    /**
     * Gets rule.
     *
     * @return the rule
     */
    public Map getRule() {
        return rule;
    }

    /**
     * Sets rule.
     *
     * @param rule the rule
     */
    public void setRule(Map rule) {
        this.rule = rule;
    }

    /**
     * Gets actives.
     *
     * @return the actives
     */
    public Object getActives() {
        return actives;
    }

    /**
     * Sets actives.
     *
     * @param actives the actives
     */
    public void setActives(Object actives) {
        this.actives = actives;
    }

    /**
     * Gets service instance id.
     *
     * @return the service instance id
     */
    public String getServiceInstanceId() {
        return serviceInstanceId;
    }

    /**
     * Sets service instance id.
     *
     * @param serviceInstanceId the service instance id
     */
    public void setServiceInstanceId(String serviceInstanceId) {
        this.serviceInstanceId = serviceInstanceId;
    }

    /**
     * Gets activated q profiles.
     *
     * @return the activated q profiles
     */
    public List getActivatedQProfiles() {
        return activatedQProfiles;
    }

    /**
     * Sets activated q profiles.
     *
     * @param activatedQProfiles the activated q profiles
     */
    public void setActivatedQProfiles(List activatedQProfiles) {
        this.activatedQProfiles = activatedQProfiles;
    }

    @Override
    public String toString() {
        return "CodingRules{" +
                "key='" + key + '\'' +
                ", qprofile='" + qprofile + '\'' +
                ", ps=" + ps +
                ", p=" + p +
                ", f='" + f + '\'' +
                ", facets=" + facets +
                ", activation='" + activation + '\'' +
                ", total=" + total +
                ", custom_key='" + custom_key + '\'' +
                ", markdown_description='" + markdown_description + '\'' +
                ", name='" + name + '\'' +
                ", language='" + language + '\'' +
                ", actives=" + actives +
                ", resultStatus='" + resultStatus + '\'' +
                ", resultMessage='" + resultMessage + '\'' +
                ", languages='" + languages + '\'' +
                ", s='" + s + '\'' +
                ", asc='" + asc + '\'' +
                ", severities='" + severities + '\'' +
                ", rules=" + rules +
                ", condition=" + condition +
                ", q='" + q + '\'' +
                ", rule=" + rule +
                ", serviceInstanceId='" + serviceInstanceId + '\'' +
                ", activatedQProfiles=" + activatedQProfiles +
                '}';
    }
}
