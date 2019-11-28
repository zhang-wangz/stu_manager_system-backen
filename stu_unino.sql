CREATE TABLE user(
		uid varchar (20) NOT NULL ,
		username varchar(20)  NOT NULL,
		pwd varchar(100)  NOT NULL,
		branch varchar(20)  NOT NULL,
		class varchar(20)  NOT NULL,
		sex	varchar(5) NOT NULL,
		phonenumber	int(11) NOT NULL,
		email	varchar(20) ,
		rank	varchar(5) not null,
		departments	varchar(20) not NULL,
		leavetime	timestamp(6),
		appointment_time timestamp(6) not NULL,
		extr	varchar(100) ,
		PRIMARY KEY(uid)
);



CREATE TABLE department(
    departmentid varchar(20) not null ,
    departmentname varchar (10) not null,
    ministerid varchar(20) not null,
    chairmanid varchar(20) not null,
    peoplenumber int(20) not null,
    introduction varchar(100) not null,
    createtime timestamp(6) not null,
    deletetime timestamp (6) null,
    primary key (departmentid),
    foreign key (ministerid) references user(uid) on delete cascade on update cascade,
    foreign key (chairmanid) references user(uid) on delete cascade on update cascade
)

CREATE TABLE activity(
    activityid varchar(20) not null,
    activityname varchar(10) not null,
    peopleamount int(10) not null,
    starttime TIMESTAMP (6) not null,
    overtime TIMESTAMP (6) not null,
    primary key(activityid)
)


CREATE TABLE judge(
    judgeid varchar(20) not null,
    judgetype int(10) not null,
    judgecontentid varchar(10) not null,
    isjudge int(1),
    primary key(judgeid),
    foreign key(judgeid) references on activity(activityid) on delete cascade on update cascade
)

CREATE TABLE notice(
    informid varchar(20) not null,
    informerid varchar(20) not null,
    informtype varchar(20) not null,
    userid varchar (10) not null,
    checknum int(50) not null,
    primary key(informid),
    foreign key(informerid) references on activity(activityid) on delete cascade on update cascade,
    foreign key (userid) references user(uid) on delete cascade on update cascade
)

CREATE TABLE qingjia(
    vacateid varchar(20) not null,
    vacateperson varchar(19) not null,
    persondepartment varchar(19) not null,
    vacatereason varchar(19) not null,
    leavetime TIMESTAMP (6) not null,
    backtime TIMESTAMP (6) not null,
    judgeid varchar(19) not null,
    primary key(vacateid)
)
