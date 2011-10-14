package com.jakewharton.trakt.entities;

import java.util.Calendar;
import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.jakewharton.trakt.TraktEntity;
import com.jakewharton.trakt.enumerations.Gender;

public final class UserProfile implements TraktEntity {
	private static final long serialVersionUID = -4145012978937162733L;
	
	public static final class Stats implements TraktEntity {
		private static final long serialVersionUID = -2737256634772977389L;
		
		public static final class Shows implements TraktEntity {
			private static final long serialVersionUID = -2888630218268563052L;
			
			private Integer library;
			
			public Integer getLibrary() {
				return this.library;
			}
			public void setLibrary(Integer library) {
				this.library = library;
			}
		}
		public static final class Episodes implements TraktEntity {
			private static final long serialVersionUID = 7210925664642958187L;
			
			private Integer watched;
			@SerializedName("watched_unique") private Integer watchedUnique;
			@SerializedName("watched_trakt") private Integer watchedTrakt;
			@SerializedName("watched_trakt_unique") private Integer watchedTraktUnique;
			@SerializedName("watched_elsewhere") private Integer watchedElsewhere;
			private Integer unwatched;
			
			public Integer getWatched() {
				return this.watched;
			}
			public void setWatched(Integer watched) {
				this.watched = watched;
			}
			public Integer getWatchedUnique() {
				return this.watchedUnique;
			}
			public void setWatchedUnique(Integer watchedUnique) {
				this.watchedUnique = watchedUnique;
			}
			public Integer getWatchedTrakt() {
				return this.watchedTrakt;
			}
			public void setWatchedTrakt(Integer watchedTrakt) {
				this.watchedTrakt = watchedTrakt;
			}
			public Integer getWatchedTraktUnique() {
				return this.watchedTraktUnique;
			}
			public void setWatchedTraktUnique(Integer watchedTraktUnique) {
				this.watchedTraktUnique = watchedTraktUnique;
			}
			public Integer getWatchedElsewhere() {
				return this.watchedElsewhere;
			}
			public void setWatchedElsewhere(Integer watchedElsewhere) {
				this.watchedElsewhere = watchedElsewhere;
			}
			public Integer getUnwatched() {
				return this.unwatched;
			}
			public void setUnwatched(Integer unwatched) {
				this.unwatched = unwatched;
			}
		}
		public static final class Movies implements TraktEntity {
			private static final long serialVersionUID = 5061541628681754141L;
			
			private Integer watched;
			@SerializedName("watched_unique") private Integer watchedUnique;
			@SerializedName("watched_trakt") private Integer watchedTrakt;
			@SerializedName("watched_trakt_unique") private Integer watchedTraktUnique;
			@SerializedName("watched_elsewhere") private Integer watchedElsewhere;
			private Integer library;
			private Integer unwatched;
			
			public Integer getWatched() {
				return this.watched;
			}
			public void setWatched(Integer watched) {
				this.watched = watched;
			}
			public Integer getWatchedUnique() {
				return this.watchedUnique;
			}
			public void setWatchedUnique(Integer watchedUnique) {
				this.watchedUnique = watchedUnique;
			}
			public Integer getWatchedTrakt() {
				return this.watchedTrakt;
			}
			public void setWatchedTrakt(Integer watchedTrakt) {
				this.watchedTrakt = watchedTrakt;
			}
			public Integer getWatchedTraktUnique() {
				return this.watchedTraktUnique;
			}
			public void setWatchedTraktUnique(Integer watchedTraktUnique) {
				this.watchedTraktUnique = watchedTraktUnique;
			}
			public Integer getWatchedElsewhere() {
				return this.watchedElsewhere;
			}
			public void setWatchedElsewhere(Integer watchedElsewhere) {
				this.watchedElsewhere = watchedElsewhere;
			}
			public Integer getLibrary() {
				return this.library;
			}
			public void setLibrary(Integer library) {
				this.library = library;
			}
			public Integer getUnwatched() {
				return this.unwatched;
			}
			public void setUnwatched(Integer unwatched) {
				this.unwatched = unwatched;
			}
		}
		
		private Integer friends;
		private Shows shows;
		private Episodes episodes;
		private Movies movies;
		
		public Integer getFriends() {
			return this.friends;
		}
		public void setFriends(Integer friends) {
			this.friends = friends;
		}
		public Shows getShows() {
			return this.shows;
		}
		public void setShows(Shows shows) {
			this.shows = shows;
		}
		public Episodes getEpisodes() {
			return this.episodes;
		}
		public void setEpisodes(Episodes episodes) {
			this.episodes = episodes;
		}
		public Movies getMovies() {
			return this.movies;
		}
		public void setMovies(Movies movies) {
			this.movies = movies;
		}
	}

	private String username;
	@SerializedName("protected") private Boolean _protected;
	@SerializedName("full_name") private String fullName;
	private Gender gender;
	private Integer age;
	private String location;
	private String about;
	private Calendar joined;
	private String avatar;
	private String url;
	private Stats stats;
	private WatchedMediaEntity watching;
	private List<MediaEntity> watched;
	private Integer plays;
	private TvShowEpisode episode;
	private Calendar approved;
	
	public String getUsername() {
		return this.username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Boolean getProtected() {
		return this._protected;
	}
	public void setProtected(Boolean _protected) {
		this._protected = _protected;
	}
	public String getFullName() {
		return this.fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public Gender getGender() {
		return this.gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public Integer getAge() {
		return this.age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getLocation() {
		return this.location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getAbout() {
		return this.about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	public Calendar getJoined() {
		return this.joined;
	}
	public void setJoined(Calendar joined) {
		this.joined = joined;
	}
	public String getAvatar() {
		return this.avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getUrl() {
		return this.url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Stats getStats() {
		return this.stats;
	}
	public void setStats(Stats stats) {
		this.stats = stats;
	}
	public MediaEntity getWatching() {
		return this.watching;
	}
	public void setWatching(WatchedMediaEntity watching) {
		this.watching = watching;
	}
	public List<MediaEntity> getWatched() {
		return this.watched;
	}
	public void setWatched(List<MediaEntity> watched) {
		this.watched = watched;
	}
	public Integer getPlays() {
		return this.plays;
	}
	public void setPlays(Integer plays) {
		this.plays = plays;
	}
	public TvShowEpisode getEpisode() {
		return this.episode;
	}
	public void setEpisode(TvShowEpisode episode) {
		this.episode = episode;
	}
	public Calendar getApproved() {
		return this.approved;
	}
	public void setApproved(Calendar approved) {
		this.approved = approved;
	}
}
