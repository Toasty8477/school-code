package edu.msoe.swe2721.taxRateCalculator;

class TaxCalculator2023 implements TaxCalculatorInterface {
    double adjustedGrossIncome;
    final int age;
    final FilingStatus filingStatus;
    final String name;
    final int spouseAge;
    final String spouseName;

    final double[] HEAD_OF_HOUSE_RANGE = { 15700, 59850, 95350, 182100, 231250, 578100 };
    final double[] MARRIED_JOINT_RANGE = { 22000, 89450, 190750, 364200, 462500, 693750 };
    final double[] SINGLE_ISH = { 11000, 44725, 95375, 182100, 231250, 578125 };

    public TaxCalculator2023(String name, String spouseName, FilingStatus filingStatus, int age, int spouseAge)
            throws TaxFilingException {
        if (age <= 0) {
            throw new TaxFilingException("Age must be over 0");
        }

        if (name.split(" ").length < 2) {
            throw new TaxFilingException("Need last name");
        }

        if (name.split(" ")[0].length() == 1) {
            throw new TaxFilingException("Invalid first name");
        }

        if (name.split(" ")[1].length() == 1) {
            throw new TaxFilingException("Invalid last name");
        }

        if (filingStatus == FilingStatus.SINGLE || filingStatus == FilingStatus.HEAD_OF_HOUSEHOLD) {
            if (spouseName != null) {
                throw new TaxFilingException("No Spouse should be provided");
            }
        }

        if (filingStatus == FilingStatus.MARRIED_FILING_JOINTLY
                || filingStatus == FilingStatus.MARRIED_FILING_SEPARATELY) {

            if (spouseAge <= 0) {
                throw new TaxFilingException("Age must be over 0");
            }

            if (spouseName == null) {
                throw new TaxFilingException("No Spouse found for married");
            }

            if (spouseName.split(" ").length < 2) {
                throw new TaxFilingException("Need spouse last name");
            }

            if (spouseName.split(" ")[0].length() == 1) {
                throw new TaxFilingException("Invalid spouse first name");
            }

            if (spouseName.split(" ")[1].length() == 1) {
                throw new TaxFilingException("Invalid spouse last name");
            }
        }

        this.name = name;
        this.spouseName = spouseName;
        this.filingStatus = filingStatus;
        this.age = age;
        this.spouseAge = spouseAge;
    }

    public TaxCalculator2023(String name, FilingStatus filingStatus, int age) throws TaxFilingException {
        this(name, null, filingStatus, age, 0);
    }

    @Override
    public double getAdjustedGrossIncome() {
        return adjustedGrossIncome;
    }

    @Override
    public void setAdjustedGrossIncome(double adjustedGrossIncome) throws TaxFilingException {
        if (adjustedGrossIncome < 0) {
            throw new TaxFilingException("Adjusted gross income must be greater than or equal to zero");
        }

        this.adjustedGrossIncome = adjustedGrossIncome;
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public FilingStatus getFilingStatus() {
        return filingStatus;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getSpouseAge() {
        return spouseAge;
    }

    @Override
    public String getSpouseName() {
        return spouseName;
    }

    @Override
    public double getTaxDue() {
        double income = obtainTaxableIncome();
        double tax = 0.0;

        if (filingStatus == FilingStatus.HEAD_OF_HOUSEHOLD) {
            if (income <= 15700) {
                tax = (income * 0.1);
            } else if (income > 15700 && income <= 59850) {
                tax = (income - 15700) * 0.12 + 1570;
            } else if (income > 59850 && income <= 95350) {
                tax = (income - 59850) * 0.22 + 6868;
            } else if (income > 95350 && income <= 182100) {
                tax = (income - 95350) * 0.24 + 14678;
            } else if (income > 182100 && income <= 231250) {
                tax = (income - 182100) * 0.32 + 35498;
            } else if (income > 231250 && income <= 578100) {
                tax = (income - 231250) * 0.35 + 51226;
            } else if (income > 578100) {
                tax = (income - 578100) * 0.37 + 172623.5;
            }
        } else if (filingStatus == FilingStatus.MARRIED_FILING_JOINTLY) {
            if (income <= 22000) {
                tax = (income * 0.1);
            } else if (income > 22_000 && income <= 89_450) {
                tax = (income - 22_000) * 0.12 + 2200;
            } else if (income > 89_450 && income <= 190_750) {
                tax = (income - 89_450) * 0.22 + 10_294;
            } else if (income > 190_750 && income <= 364_200) {
                tax = (income - 190_750) * 0.24 + 32_580;
            } else if (income > 364_200 && income <= 462_500) {
                tax = (income - 364_200) * 0.32 + 74_208;
            } else if (income > 462_500 && income <= 693_750) {
                tax = (income - 462_500) * 0.35 + 105_664;
            } else if (income > 693_750) {
                tax = (income - 693_750) * 0.37 + 186_601.5;
            }
        } else {
            if (income <= 11000) {
                tax = (income * 0.1);
            } else if (income > 11000 && income <= 44725) {
                tax = (income - 11000) * 0.12 + 1100;
            } else if (income > 44725 && income <= 95_375) {
                tax = (income - 44725) * 0.22 + 5147;
            } else if (income > 95_375 && income <= 182_100) {
                tax = (income - 95_375) * 0.24 + 16_290;
            } else if (income > 182_100 && income <= 231_250) {
                tax = (income - 182_100) * 0.32 + 37_104;
            } else if (income > 231_250 && income <= 578_125) {
                tax = (income - 231_250) * 0.35 + 52_832;
            } else if (income > 578_125) {
                tax = (income - 578_125) * 0.37 + 174_238.25;
            }
        }

        return tax;
    }

    @Override
    public double getNetTaxRate() {
        return (getTaxDue() / adjustedGrossIncome) * 100;
    }

    @Override
    public boolean determineFilingNeed() {
        switch (filingStatus) {
            case HEAD_OF_HOUSEHOLD:
                if (age < 65) {
                    return adjustedGrossIncome >= 20800;
                }
                return adjustedGrossIncome >= 22650;
            case MARRIED_FILING_JOINTLY:
                if (age < 65) {
                    if (spouseAge < 65) {
                        return adjustedGrossIncome >= 27700;
                    }
                    return adjustedGrossIncome >= 29200;
                }

                if (spouseAge < 65) {
                    return adjustedGrossIncome >= 29200;
                }
                return adjustedGrossIncome >= 30700;

            case MARRIED_FILING_SEPARATELY:
                return adjustedGrossIncome >= 5;

            default: // single
                if (age < 65) {
                    return adjustedGrossIncome >= 13850;
                }
                return adjustedGrossIncome >= 15700;
        }
    }

    @Override
    public double obtainStandardDeduction() {
        switch (filingStatus) {
            case HEAD_OF_HOUSEHOLD:
                if (age < 65) {
                    return 20800;
                }
                return 22650;
            case MARRIED_FILING_JOINTLY:
                if (age < 65) {
                    if (spouseAge < 65) {
                        return 27700;
                    }
                    return 29200;
                }
                if (spouseAge < 65) {
                    return 29200;
                }
                return 30700;
            case MARRIED_FILING_SEPARATELY:
                if (age < 65) {
                    return 13850;
                }
                return 15350;
            default: // single
                if (age < 65) {
                    return 13850;
                }
                return 15700;
        }

    }

    @Override
    public double obtainTaxableIncome() {
        double taxableIncome = adjustedGrossIncome - obtainStandardDeduction();

        if (taxableIncome < 0) {
            return 0;
        }

        return taxableIncome;
    }
}
