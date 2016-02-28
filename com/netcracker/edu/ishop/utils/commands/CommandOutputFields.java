package netcracker.edu.ishop.utils.commands;

public enum CommandOutputFields {
    STATUS {
        @Override
        public String toString() {
            return "STATUS";
        }
    },
    CODE {
        @Override
        public String toString() {
            return "CODE";
        }
    },
    CONTENT {
        @Override
        public String toString() {
            return "Content";
        }
    };
}
