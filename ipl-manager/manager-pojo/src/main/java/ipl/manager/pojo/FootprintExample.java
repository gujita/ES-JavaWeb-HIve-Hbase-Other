package ipl.manager.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FootprintExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public FootprintExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Long value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Long value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Long value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Long value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Long value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Long value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Long> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Long> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Long value1, Long value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Long value1, Long value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andSearchContentIsNull() {
            addCriterion("search_content is null");
            return (Criteria) this;
        }

        public Criteria andSearchContentIsNotNull() {
            addCriterion("search_content is not null");
            return (Criteria) this;
        }

        public Criteria andSearchContentEqualTo(String value) {
            addCriterion("search_content =", value, "searchContent");
            return (Criteria) this;
        }

        public Criteria andSearchContentNotEqualTo(String value) {
            addCriterion("search_content <>", value, "searchContent");
            return (Criteria) this;
        }

        public Criteria andSearchContentGreaterThan(String value) {
            addCriterion("search_content >", value, "searchContent");
            return (Criteria) this;
        }

        public Criteria andSearchContentGreaterThanOrEqualTo(String value) {
            addCriterion("search_content >=", value, "searchContent");
            return (Criteria) this;
        }

        public Criteria andSearchContentLessThan(String value) {
            addCriterion("search_content <", value, "searchContent");
            return (Criteria) this;
        }

        public Criteria andSearchContentLessThanOrEqualTo(String value) {
            addCriterion("search_content <=", value, "searchContent");
            return (Criteria) this;
        }

        public Criteria andSearchContentLike(String value) {
            addCriterion("search_content like", value, "searchContent");
            return (Criteria) this;
        }

        public Criteria andSearchContentNotLike(String value) {
            addCriterion("search_content not like", value, "searchContent");
            return (Criteria) this;
        }

        public Criteria andSearchContentIn(List<String> values) {
            addCriterion("search_content in", values, "searchContent");
            return (Criteria) this;
        }

        public Criteria andSearchContentNotIn(List<String> values) {
            addCriterion("search_content not in", values, "searchContent");
            return (Criteria) this;
        }

        public Criteria andSearchContentBetween(String value1, String value2) {
            addCriterion("search_content between", value1, value2, "searchContent");
            return (Criteria) this;
        }

        public Criteria andSearchContentNotBetween(String value1, String value2) {
            addCriterion("search_content not between", value1, value2, "searchContent");
            return (Criteria) this;
        }

        public Criteria andSearchTimeIsNull() {
            addCriterion("search_time is null");
            return (Criteria) this;
        }

        public Criteria andSearchTimeIsNotNull() {
            addCriterion("search_time is not null");
            return (Criteria) this;
        }

        public Criteria andSearchTimeEqualTo(Date value) {
            addCriterion("search_time =", value, "searchTime");
            return (Criteria) this;
        }

        public Criteria andSearchTimeNotEqualTo(Date value) {
            addCriterion("search_time <>", value, "searchTime");
            return (Criteria) this;
        }

        public Criteria andSearchTimeGreaterThan(Date value) {
            addCriterion("search_time >", value, "searchTime");
            return (Criteria) this;
        }

        public Criteria andSearchTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("search_time >=", value, "searchTime");
            return (Criteria) this;
        }

        public Criteria andSearchTimeLessThan(Date value) {
            addCriterion("search_time <", value, "searchTime");
            return (Criteria) this;
        }

        public Criteria andSearchTimeLessThanOrEqualTo(Date value) {
            addCriterion("search_time <=", value, "searchTime");
            return (Criteria) this;
        }

        public Criteria andSearchTimeIn(List<Date> values) {
            addCriterion("search_time in", values, "searchTime");
            return (Criteria) this;
        }

        public Criteria andSearchTimeNotIn(List<Date> values) {
            addCriterion("search_time not in", values, "searchTime");
            return (Criteria) this;
        }

        public Criteria andSearchTimeBetween(Date value1, Date value2) {
            addCriterion("search_time between", value1, value2, "searchTime");
            return (Criteria) this;
        }

        public Criteria andSearchTimeNotBetween(Date value1, Date value2) {
            addCriterion("search_time not between", value1, value2, "searchTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}