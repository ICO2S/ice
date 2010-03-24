package org.jbei.ice.lib.vo;

public interface IPartValueObject extends IEntryValueObject {
    String getPackageFormat();

    void setPackageFormat(String packageFormat);

    String getPkgdDnaFwdHash();

    void setPkgdDnaFwdHash(String pkgdDnaFwdHash);

    String getPkgdDnaRevHash();

    void setPkgdDnaRevHash(String pkgdDnaRevHash);
}