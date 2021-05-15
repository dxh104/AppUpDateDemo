package com.example.app_baselibrary;

import java.util.List;

/**
 * Created by XHD on 2020/08/18
 */
public class CalendarInfo {

    /**
     * status : 0
     * data : [{"datekey":"2016/5/5 0:00:00","festivals":"","vacationStatus":"0","terms":"立夏","termBeginTime":"09时41分50秒","yi":"打扫 沐浴 破屋 馀事勿取 坏垣","ji":"诸事不宜","pzbj":"丁不剃头头必生疮 亥不嫁娶不利新郎","jsyq":"驿马 天后 天仓 不将 金堂 宝光","xsyj":"月破 大耗 四穷 七鸟 往亡 重日","cs":"猪日冲蛇 煞西","wx":"屋上土","cxfgod":"喜神在西南 财神在正东 福神在西南","tgdz":"丙申年 [猴] 癸巳月 丁亥日","weekDay":"Thu","lunar":"三月廿九","weeks":"周四 第19周","constellation":"金牛座","branchHourInfo":"{\"0\":{\"bh\":\"庚子时\",\"cs\":\"冲马煞南\",\"cxfgod\":\"喜神西北 财神正东 福神西南\",\"j\":\"动土 修造\",\"y\":\"结婚 出行 搬家 开业 赴任 安葬 求财\"},\"1\":{\"bh\":\"辛丑时\",\"cs\":\"冲羊煞东\",\"cxfgod\":\"喜神西南 财神正东 福神西南\",\"j\":\"-\",\"y\":\"结婚 出行 搬家 搬新房 开业 赴任 安床 开仓 盖屋 修造 收养子女 求财\"},\"10\":{\"bh\":\"庚戌时\",\"cs\":\"冲龙煞北\",\"cxfgod\":\"喜神西北 财神正东 福神西南\",\"j\":\"动土 祈福 祭祀 修造 酬神 斋醮\",\"y\":\"结婚 开业 安葬\"},\"11\":{\"bh\":\"辛亥时\",\"cs\":\"冲蛇煞西\",\"cxfgod\":\"喜神西南 财神正东 福神西南\",\"j\":\"出行 赴任\",\"y\":\"祈福 祭祀 酬神 求财\"},\"2\":{\"bh\":\"壬寅时\",\"cs\":\"冲猴煞北\",\"cxfgod\":\"喜神正南 财神正南 福神西北\",\"j\":\"祈福 祭祀 酬神 斋醮\",\"y\":\"结婚 安葬 修造 合嵴\"},\"3\":{\"bh\":\"癸卯时\",\"cs\":\"冲鸡煞西\",\"cxfgod\":\"喜神东南 财神正南 福神正西\",\"j\":\"出行 赴任 动土 祈福 祭祀 修造 开光 斋醮\",\"y\":\"结婚 交易 开业 安床 求子 求财\"},\"4\":{\"bh\":\"甲辰时\",\"cs\":\"冲狗煞南\",\"cxfgod\":\"喜神东北 财神东北 福神东南\",\"j\":\"-\",\"y\":\"结婚 出行 搬家 赴任 祈福 安葬 祭祀 修造 作灶 酬神 收养子女 斋醮 求财\"},\"5\":{\"bh\":\"乙巳时\",\"cs\":\"冲猪煞东\",\"cxfgod\":\"喜神西北 财神西南 福神东南\",\"j\":\"诸事不宜\",\"y\":\"-\"},\"6\":{\"bh\":\"丙午时\",\"cs\":\"冲鼠煞北\",\"cxfgod\":\"喜神西南 财神正西 福神正东\",\"j\":\"动土 修造\",\"y\":\"结婚 出行 祈福 安葬 求子 求财\"},\"7\":{\"bh\":\"丁未时\",\"cs\":\"冲牛煞西\",\"cxfgod\":\"喜神正南 财神正西 福神正东\",\"j\":\"-\",\"y\":\"结婚 出行 交易 搬新房 开业 祈福 安床 安葬 祭祀 修造 求子 求财\"},\"8\":{\"bh\":\"戊申时\",\"cs\":\"冲虎煞南\",\"cxfgod\":\"喜神东南 财神正北 福神正北\",\"j\":\"赴任 诉讼 祈福 乘船 求子\",\"y\":\"搬新房 安葬 修造\"},\"9\":{\"bh\":\"己酉时\",\"cs\":\"冲兔煞东\",\"cxfgod\":\"喜神东北 财神正北 福神正南\",\"j\":\"诸事不宜\",\"y\":\"结婚 出行 祈福 安葬 祭祀 修造 酬神 求财\"}}","shichengjixiong":"{\"丁未\":\"吉\",\"丙午\":\"吉\",\"乙巳\":\"凶\",\"壬寅\":\"凶\",\"己酉\":\"凶\",\"庚子\":\"凶\",\"庚戌\":\"吉\",\"戊申\":\"凶\",\"甲辰\":\"吉\",\"癸卯\":\"凶\",\"辛丑\":\"吉\",\"辛亥\":\"吉\"}","zs":"天德","shenershen":"破日","erba":"井木犴宿星","taishen":"仓库床外 西北","isrun":0,"url":"http://www.51wnl.com/cal4qq/index.html"}]
     * errMsg :
     */

    private int status;
    private String errMsg;
    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * datekey : 2016/5/5 0:00:00
         * festivals :
         * vacationStatus : 0
         * terms : 立夏
         * termBeginTime : 09时41分50秒
         * yi : 打扫 沐浴 破屋 馀事勿取 坏垣
         * ji : 诸事不宜
         * pzbj : 丁不剃头头必生疮 亥不嫁娶不利新郎
         * jsyq : 驿马 天后 天仓 不将 金堂 宝光
         * xsyj : 月破 大耗 四穷 七鸟 往亡 重日
         * cs : 猪日冲蛇 煞西
         * wx : 屋上土
         * cxfgod : 喜神在西南 财神在正东 福神在西南
         * tgdz : 丙申年 [猴] 癸巳月 丁亥日
         * weekDay : Thu
         * lunar : 三月廿九
         * weeks : 周四 第19周
         * constellation : 金牛座
         * branchHourInfo : {"0":{"bh":"庚子时","cs":"冲马煞南","cxfgod":"喜神西北 财神正东 福神西南","j":"动土 修造","y":"结婚 出行 搬家 开业 赴任 安葬 求财"},"1":{"bh":"辛丑时","cs":"冲羊煞东","cxfgod":"喜神西南 财神正东 福神西南","j":"-","y":"结婚 出行 搬家 搬新房 开业 赴任 安床 开仓 盖屋 修造 收养子女 求财"},"10":{"bh":"庚戌时","cs":"冲龙煞北","cxfgod":"喜神西北 财神正东 福神西南","j":"动土 祈福 祭祀 修造 酬神 斋醮","y":"结婚 开业 安葬"},"11":{"bh":"辛亥时","cs":"冲蛇煞西","cxfgod":"喜神西南 财神正东 福神西南","j":"出行 赴任","y":"祈福 祭祀 酬神 求财"},"2":{"bh":"壬寅时","cs":"冲猴煞北","cxfgod":"喜神正南 财神正南 福神西北","j":"祈福 祭祀 酬神 斋醮","y":"结婚 安葬 修造 合嵴"},"3":{"bh":"癸卯时","cs":"冲鸡煞西","cxfgod":"喜神东南 财神正南 福神正西","j":"出行 赴任 动土 祈福 祭祀 修造 开光 斋醮","y":"结婚 交易 开业 安床 求子 求财"},"4":{"bh":"甲辰时","cs":"冲狗煞南","cxfgod":"喜神东北 财神东北 福神东南","j":"-","y":"结婚 出行 搬家 赴任 祈福 安葬 祭祀 修造 作灶 酬神 收养子女 斋醮 求财"},"5":{"bh":"乙巳时","cs":"冲猪煞东","cxfgod":"喜神西北 财神西南 福神东南","j":"诸事不宜","y":"-"},"6":{"bh":"丙午时","cs":"冲鼠煞北","cxfgod":"喜神西南 财神正西 福神正东","j":"动土 修造","y":"结婚 出行 祈福 安葬 求子 求财"},"7":{"bh":"丁未时","cs":"冲牛煞西","cxfgod":"喜神正南 财神正西 福神正东","j":"-","y":"结婚 出行 交易 搬新房 开业 祈福 安床 安葬 祭祀 修造 求子 求财"},"8":{"bh":"戊申时","cs":"冲虎煞南","cxfgod":"喜神东南 财神正北 福神正北","j":"赴任 诉讼 祈福 乘船 求子","y":"搬新房 安葬 修造"},"9":{"bh":"己酉时","cs":"冲兔煞东","cxfgod":"喜神东北 财神正北 福神正南","j":"诸事不宜","y":"结婚 出行 祈福 安葬 祭祀 修造 酬神 求财"}}
         * shichengjixiong : {"丁未":"吉","丙午":"吉","乙巳":"凶","壬寅":"凶","己酉":"凶","庚子":"凶","庚戌":"吉","戊申":"凶","甲辰":"吉","癸卯":"凶","辛丑":"吉","辛亥":"吉"}
         * zs : 天德
         * shenershen : 破日
         * erba : 井木犴宿星
         * taishen : 仓库床外 西北
         * isrun : 0
         * url : http://www.51wnl.com/cal4qq/index.html
         */

        private String datekey;
        private String festivals;
        private String vacationStatus;
        private String terms;
        private String termBeginTime;
        private String yi;
        private String ji;
        private String pzbj;
        private String jsyq;
        private String xsyj;
        private String cs;
        private String wx;
        private String cxfgod;
        private String tgdz;
        private String weekDay;
        private String lunar;
        private String weeks;
        private String constellation;
        private String branchHourInfo;
        private String shichengjixiong;
        private String zs;
        private String shenershen;
        private String erba;
        private String taishen;
        private int isrun;
        private String url;

        public String getDatekey() {
            return datekey;
        }

        public void setDatekey(String datekey) {
            this.datekey = datekey;
        }

        public String getFestivals() {
            return festivals;
        }

        public void setFestivals(String festivals) {
            this.festivals = festivals;
        }

        public String getVacationStatus() {
            return vacationStatus;
        }

        public void setVacationStatus(String vacationStatus) {
            this.vacationStatus = vacationStatus;
        }

        public String getTerms() {
            return terms;
        }

        public void setTerms(String terms) {
            this.terms = terms;
        }

        public String getTermBeginTime() {
            return termBeginTime;
        }

        public void setTermBeginTime(String termBeginTime) {
            this.termBeginTime = termBeginTime;
        }

        public String getYi() {
            return yi;
        }

        public void setYi(String yi) {
            this.yi = yi;
        }

        public String getJi() {
            return ji;
        }

        public void setJi(String ji) {
            this.ji = ji;
        }

        public String getPzbj() {
            return pzbj;
        }

        public void setPzbj(String pzbj) {
            this.pzbj = pzbj;
        }

        public String getJsyq() {
            return jsyq;
        }

        public void setJsyq(String jsyq) {
            this.jsyq = jsyq;
        }

        public String getXsyj() {
            return xsyj;
        }

        public void setXsyj(String xsyj) {
            this.xsyj = xsyj;
        }

        public String getCs() {
            return cs;
        }

        public void setCs(String cs) {
            this.cs = cs;
        }

        public String getWx() {
            return wx;
        }

        public void setWx(String wx) {
            this.wx = wx;
        }

        public String getCxfgod() {
            return cxfgod;
        }

        public void setCxfgod(String cxfgod) {
            this.cxfgod = cxfgod;
        }

        public String getTgdz() {
            return tgdz;
        }

        public void setTgdz(String tgdz) {
            this.tgdz = tgdz;
        }

        public String getWeekDay() {
            return weekDay;
        }

        public void setWeekDay(String weekDay) {
            this.weekDay = weekDay;
        }

        public String getLunar() {
            return lunar;
        }

        public void setLunar(String lunar) {
            this.lunar = lunar;
        }

        public String getWeeks() {
            return weeks;
        }

        public void setWeeks(String weeks) {
            this.weeks = weeks;
        }

        public String getConstellation() {
            return constellation;
        }

        public void setConstellation(String constellation) {
            this.constellation = constellation;
        }

        public String getBranchHourInfo() {
            return branchHourInfo;
        }

        public void setBranchHourInfo(String branchHourInfo) {
            this.branchHourInfo = branchHourInfo;
        }

        public String getShichengjixiong() {
            return shichengjixiong;
        }

        public void setShichengjixiong(String shichengjixiong) {
            this.shichengjixiong = shichengjixiong;
        }

        public String getZs() {
            return zs;
        }

        public void setZs(String zs) {
            this.zs = zs;
        }

        public String getShenershen() {
            return shenershen;
        }

        public void setShenershen(String shenershen) {
            this.shenershen = shenershen;
        }

        public String getErba() {
            return erba;
        }

        public void setErba(String erba) {
            this.erba = erba;
        }

        public String getTaishen() {
            return taishen;
        }

        public void setTaishen(String taishen) {
            this.taishen = taishen;
        }

        public int getIsrun() {
            return isrun;
        }

        public void setIsrun(int isrun) {
            this.isrun = isrun;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    @Override
    public String toString() {
        return "CalendarInfo{" +
                "status=" + status +
                ", errMsg='" + errMsg + '\'' +
                ", data=" + data +
                '}';
    }
}
