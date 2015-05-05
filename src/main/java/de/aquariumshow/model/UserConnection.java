package de.aquariumshow.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by magnus on 18/08/14.
 */
@Entity
@Table(name = "userconnection")
public class UserConnection {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "userid")
    private final String userId;

	@Column(name = "providerid")
    private final String providerId;
	
	@Column(name = "provideruserid")
    private final String providerUserId;
	
	@Column(name = "rank")
    private final int rank;
	
	@Column(name = "displayname")
    private final String displayName;
	
	@Column(name = "profileurl")
    private final String profileUrl;
	
	@Column(name = "imageurl")
    private final String imageUrl;
	
	@Column(name = "accesstoken")
    private final String accessToken;
	
	@Column(name = "secret")
    private final String secret;
	
	@Column(name = "refreshtoken")
    private final String refreshToken;
	
	@Column(name = "expiretime")
    private final Long expireTime;

    public UserConnection(String userId, String providerId, String providerUserId, int rank, String displayName, String profileUrl, String imageUrl, String accessToken, String secret, String refreshToken, Long expireTime) {
        this.userId = userId;
        this.providerId = providerId;
        this.providerUserId = providerUserId;
        this.rank = rank;
        this.displayName = displayName;
        this.profileUrl = profileUrl;
        this.imageUrl = imageUrl;
        this.accessToken = accessToken;
        this.secret = secret;
        this.refreshToken = refreshToken;
        this.expireTime = expireTime;
    }

    public String toString() {
        return
            "userId = " + userId +
            ", providerId = " + providerId +
            ", providerUserId = " + providerUserId +
            ", rank = " + rank +
            ", displayName = " + displayName +
            ", profileUrl = " + profileUrl +
            ", imageUrl = " + imageUrl +
            ", accessToken = " + accessToken +
            ", secret = " + secret +
            ", refreshToken = " + refreshToken +
            ", expireTime = " + expireTime;
    }

    public String getUserId() {
        return userId;
    }

    public String getProviderId() {
        return providerId;
    }

    public String getProviderUserId() {
        return providerUserId;
    }

    public int getRank() {
        return rank;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getSecret() {
        return secret;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public Long getExpireTime() {
        return expireTime;
    }
}