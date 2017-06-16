package com.jd.jg.admin.config;

public class CassandraConfig {

    public String clusterName;
    public String[] seeds;
    public String[] allHosts;

    public String cassandraVersion;

    public String installPkg;
    public String installPath;
    public String commitlog_directory;
    public String data_file_directory;

    public int row_cache_size_in_mb;

    public int commitlog_segment_size_in_mb = 32;

    public String jmxremote_password_path;

    public String jmxPassword;

    public String java_home;

    public String ganglia_url;

    public String dc;

    /// Getter and Setter

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String[] getSeeds() {
        return seeds;
    }

    public void setSeeds(String[] seeds) {
        this.seeds = seeds;
    }

    public String[] getAllHosts() {
        return allHosts;
    }

    public void setAllHosts(String[] allHosts) {
        this.allHosts = allHosts;
    }

    public String getCassandraVersion() {
        return cassandraVersion;
    }

    public void setCassandraVersion(String cassandraVersion) {
        this.cassandraVersion = cassandraVersion;
    }

    public String getInstallPkg() {
        return installPkg;
    }

    public void setInstallPkg(String installPkg) {
        this.installPkg = installPkg;
    }

    public String getInstallPath() {
        return installPath;
    }

    public void setInstallPath(String installPath) {
        this.installPath = installPath;
    }

    public String getCommitlog_directory() {
        return commitlog_directory;
    }

    public void setCommitlog_directory(String commitlog_directory) {
        this.commitlog_directory = commitlog_directory;
    }

    public String getData_file_directory() {
        return data_file_directory;
    }

    public void setData_file_directory(String data_file_directory) {
        this.data_file_directory = data_file_directory;
    }

    public int getRow_cache_size_in_mb() {
        return row_cache_size_in_mb;
    }

    public void setRow_cache_size_in_mb(int row_cache_size_in_mb) {
        this.row_cache_size_in_mb = row_cache_size_in_mb;
    }

    public String getJmxremote_password_path() {
        return jmxremote_password_path;
    }

    public void setJmxremote_password_path(String jmxremote_password_path) {
        this.jmxremote_password_path = jmxremote_password_path;
    }

    public String getJmxPassword() {
        return jmxPassword;
    }

    public void setJmxPassword(String jmxPassword) {
        this.jmxPassword = jmxPassword;
    }

    public String getJava_home() {
        return java_home;
    }

    public void setJava_home(String java_home) {
        this.java_home = java_home;
    }

    public String getGanglia_url() {
        return ganglia_url;
    }

    public void setGanglia_url(String ganglia_url) {
        this.ganglia_url = ganglia_url;
    }

    public String getDc() {
        return dc;
    }

    public void setDc(String dc) {
        this.dc = dc;
    }

    public int getCommitlog_segment_size_in_mb() {
        return commitlog_segment_size_in_mb;
    }

    public void setCommitlog_segment_size_in_mb(int commitlog_segment_size_in_mb) {
        this.commitlog_segment_size_in_mb = commitlog_segment_size_in_mb;
    }
}
