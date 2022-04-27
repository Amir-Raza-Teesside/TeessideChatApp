package uk.ac.tees.aad.a0147662;

import java.util.ArrayList;

public class UserStatus {
        private String name, ProfileImage;
        private Long LastUpdated;
        private ArrayList<Status> statuses;

        public UserStatus()
        {

        }

        public UserStatus(String name, String profileImage, Long lastUpdated, ArrayList<Status> statuses) {
                this.name = name;
                ProfileImage = profileImage;
                LastUpdated = lastUpdated;
                this.statuses = statuses;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getProfileImage() {
                return ProfileImage;
        }

        public void setProfileImage(String profileImage) {
                ProfileImage = profileImage;
        }

        public Long getLastUpdated() {
                return LastUpdated;
        }

        public void setLastUpdated(Long lastUpdated) {
                LastUpdated = lastUpdated;
        }

        public ArrayList<Status> getStatuses() {
                return statuses;
        }

        public void setStatuses(ArrayList<Status> statuses) {
                this.statuses = statuses;
        }
}
