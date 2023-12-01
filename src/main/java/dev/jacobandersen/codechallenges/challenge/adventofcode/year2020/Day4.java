package dev.jacobandersen.codechallenges.challenge.adventofcode.year2020;

import dev.jacobandersen.codechallenges.challenge.adventofcode.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Day4 extends Day {
    public Day4() {
        super(2020, 4, "Passport Processing");
    }

    private List<Passport> parsePassports(boolean validate) {
        final List<List<String>> text = new ArrayList<>();
        final List<String> tmp = new ArrayList<>();
        for (String line : getInputLines()) {
            if (line.isBlank()) {
                text.add(new ArrayList<>(tmp));
                tmp.clear();
                continue;
            }
            tmp.add(line);
        }

        return text.stream().map(data -> Passport.create(data, validate)).toList();
    }

    @Override
    public String partOne() {
        return String.valueOf(parsePassports(false).stream().filter(Passport::isValid).count());
    }

    @Override
    public String partTwo() {
        return String.valueOf(parsePassports(true).stream().filter(Passport::isValid).count());
    }

    private static final class Passport {
        private String byr;
        private String iyr;
        private String eyr;
        private String hgt;
        private String hcl;
        private String ecl;
        private String pid;
        private String cid;

        public static Passport create(List<String> lines, boolean validate) {
            Passport passport = new Passport();

            for (String line : lines) {
                String[] components = line.split("\\s+");
                Arrays.asList(components).forEach(component -> {
                    String[] pieces = component.trim().split(":");
                    String key = pieces[0].trim(), value = pieces[1].trim();

                    switch (key.toLowerCase()) {
                        case "byr":
                            passport.setByr(value, validate);
                            break;
                        case "iyr":
                            passport.setIyr(value, validate);
                            break;
                        case "eyr":
                            passport.setEyr(value, validate);
                            break;
                        case "hgt":
                            passport.setHgt(value, validate);
                            break;
                        case "hcl":
                            passport.setHcl(value, validate);
                            break;
                        case "ecl":
                            passport.setEcl(value, validate);
                            break;
                        case "pid":
                            passport.setPid(value, validate);
                            break;
                        case "cid":
                            passport.setCid(value);
                            break;
                    }
                });
            }

            return passport;
        }

        public String getByr() {
            return byr;
        }

        public void setByr(String byr, boolean validate) {
            if (!validate) {
                this.byr = byr;
                return;
            }

            if (byr.length() != 4) {
                this.byr = null;
                return;
            }

            int byrInt = Integer.parseInt(byr);
            this.byr = (byrInt >= 1920 && byrInt <= 2002) ? byr : null;
        }

        public String getIyr() {
            return iyr;
        }

        public void setIyr(String iyr, boolean validate) {
            if (!validate) {
                this.iyr = iyr;
                return;
            }

            if (iyr.length() != 4) {
                this.iyr = null;
                return;
            }

            int iyrInt = Integer.parseInt(iyr);
            this.iyr = (iyrInt >= 2010 && iyrInt <= 2020) ? iyr : null;
        }

        public String getEyr() {
            return eyr;
        }

        public void setEyr(String eyr, boolean validate) {
            if (!validate) {
                this.eyr = eyr;
                return;
            }

            if (eyr.length() != 4) {
                this.eyr = null;
                return;
            }

            int eyrInt = Integer.parseInt(eyr);
            this.eyr = (eyrInt >= 2020 && eyrInt <= 2030) ? eyr : null;
        }

        public String getHgt() {
            return hgt;
        }

        public void setHgt(String hgt, boolean validate) {
            if (!validate) {
                this.hgt = hgt;
                return;
            }

            int mag = Integer.parseInt(hgt.split("cm|in")[0].trim());
            if (hgt.contains("cm")) {
                this.hgt = (mag >= 150 && mag <= 193) ? hgt : null;
            } else if (hgt.contains("in")) {
                this.hgt = (mag >= 59 && mag <= 76) ? hgt : null;
            }
        }

        public String getHcl() {
            return hcl;
        }

        public void setHcl(String hcl, boolean validate) {
            if (!validate) {
                this.hcl = hcl;
                return;
            }

            final String pattern = "0123456789abcdef";

            char[] chars = hcl.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                if (i == 0) {
                    if (chars[i] != '#') {
                        this.hcl = null;
                        return;
                    }
                } else {
                    if (pattern.indexOf(chars[i]) == -1) {
                        this.hcl = null;
                        return;
                    }
                }
            }

            this.hcl = hcl;
        }

        public String getEcl() {
            return ecl;
        }

        public void setEcl(String ecl, boolean validate) {
            if (!validate) {
                this.ecl = ecl;
                return;
            }

            List<String> possibilities = Arrays.asList("amb", "blu", "brn", "gry", "grn", "hzl", "oth");
            if (possibilities.stream().anyMatch(ecl::equals)) {
                this.ecl = ecl;
            } else {
                this.ecl = null;
            }
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid, boolean validate) {
            if (!validate) {
                this.pid = pid;
                return;
            }

            String pattern = "0123456789";

            if (pid.length() != 9) {
                this.pid = null;
                return;
            }

            char[] chars = pid.toCharArray();
            for (int i = 0; i < 9; i++) {
                if (pattern.indexOf(chars[i]) == -1) {
                    this.pid = null;
                    return;
                }
            }

            this.pid = pid;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public boolean isValid() {
            return noneNull(getByr(), getIyr(), getEyr(), getHgt(), getHcl(), getEcl(), getPid()); // Don't care if cid is missing.
        }

        private boolean noneNull(Object... objects) {
            return Arrays.stream(objects).allMatch(Objects::nonNull);
        }
    }
}
