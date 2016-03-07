package netcracker.edu.ishop.utils;

import org.apache.log4j.Logger;

import java.util.EnumSet;

public enum UserGroupTypes {


    USER {
        @Override
        public String toString() {
            return "USER";
        }
    },
    ADMIN {
        @Override
        public String toString() {
            return "ADMIN";
        }
    },
    GUEST {
        @Override
        public String toString() {
            return "GUEST";
        }
    };


    private static final Logger log = org.apache.log4j.Logger.getLogger(UserGroupTypes.class);

    //https://stackoverflow.com/questions/21754572/i-need-check-if-enum-element-is-into-enum-set
    public static EnumSet<UserGroupTypes> setGuestAccessGroup() {
        return EnumSet.of(GUEST);
    }

    public static EnumSet<UserGroupTypes> setUserAccessGroup() {
        return EnumSet.of(USER);
    }

    public static EnumSet<UserGroupTypes> setAdminAccessGroup() {
        return EnumSet.of(ADMIN);
    }

    public static EnumSet<UserGroupTypes> setAccessForSignInGroups() {
        return EnumSet.of(ADMIN, USER);
    }

    public static EnumSet<UserGroupTypes> setAllAccessGroups() {
        return EnumSet.of(ADMIN, USER, GUEST);
    }

    public static UserGroupTypes setUserGroupType(String groupType) {
        if (groupType.equals("ADMIN")) {
            return ADMIN;
        }
        else if (groupType.equals("USER")) {
            return USER;
        }
        else if (groupType.equals("GUEST")) {
            return GUEST;
        }
        else {
            log.info("Can't handle incorrect grouptype:" + groupType);
            return null;
        }
    }


}
