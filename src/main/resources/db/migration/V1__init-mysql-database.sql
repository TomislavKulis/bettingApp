    drop table if exists bet;

    drop table if exists bet_slip;

    drop table if exists fixture;

    drop table if exists wallet;

    create table bet_slip (
        bet_slip_odds float(53) not null,
        possible_winnings float(53) not null,
        stake float(53) not null,
        created_at datetime(6),
        updated_at datetime(6),
        id varchar(36) not null,
        primary key (id)
    ) engine=InnoDB;

    create table fixture (
        away_win_odds float(53) not null,
        draw_odds float(53) not null,
        home_win_odds float(53) not null,
        created_at datetime(6),
        updated_at datetime(6),
        id varchar(36) not null,
        away_team varchar(255) not null,
        home_team varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create table bet (
        id varchar(36) not null,
        outcome enum ('HOME_WIN','AWAY_WIN','DRAW'),
        match_id varchar(36) default null,
        bet_slip_id varchar(36) default null,
        primary key (id),
        constraint foreign key (match_id) references fixture (id),
        constraint foreign key (bet_slip_id) references bet_slip (id)
    ) engine=InnoDB;

    create table wallet (
        balance float(53) not null,
        created_at datetime(6),
        updated_at datetime(6),
        id varchar(36) not null,
        primary key (id)
    ) engine=InnoDB;