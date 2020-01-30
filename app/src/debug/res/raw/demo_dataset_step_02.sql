-- insert into campaign
-- insert into character header
-- insert into character body

insert into Campaign (fk_roleplaying_system_id, fk_theme_id, creation_date_millis, edit_date_millis, last_used_date_millis, archived, campaign_name, synopsis)
    values (
        '1', -- Shadowrun
        '1',
        '1360401695000', -- 02/09/2013 10:21:35
        '1525365900000', -- 05/03/2018 18:45:00
        '1528049580000', -- 06/03/2018 20:13:00
        'false',
        'Rising Dragon',
        'You had it all. Fame, Wealth and whats propably the rarest thing in
         the business: friends. Trying to keep it this way you agree to do one
         last job. You never were one to be overly supersticious, so whats the
         worst thing that could happen, right? Well, youre about to find out.'
    );

insert into Campaign (fk_roleplaying_system_id, fk_theme_id, creation_date_millis, edit_date_millis, last_used_date_millis, archived, campaign_name, synopsis)
    values (
        '2', -- DnD
        '2',
        '1456932942000', -- 03/02/2016 13:35:42
        '1517548723000', -- 02/02/2018 06:18:43
        '1517553135000', -- 02/02/2018 07:32:15
        'false',
        'Blood for Blood',
        'For years now you have been in the service of the Van Henders, one
         of the most influential families within the city. They took you and
         your brothers in when you were roaming the streets of the city as
         part of the many orphans left over by the war. You have been ever
         gratefull, till the day you stumble upon an letter written by your
         mother within her time of passing...'
    );

insert into Campaign (fk_roleplaying_system_id, fk_theme_id, creation_date_millis, edit_date_millis, last_used_date_millis, archived, campaign_name, synopsis)
    values (
        '3', -- Hunter
        '3',
        '1376420869000', -- 08/13/2013 21:07:49
        '1398155466000', -- 04/22/2014 10:31:06
        '1398189501000', -- 04/22/2014 19:58:21
        'true',
        'Endless Vigil',
        '',
        'It has been almost 2 Years now that you''ve been accepted into the order
         and all you''ve been doing is guarding that fucking box. It sounded all
         so glamerous when they convinced you all to join. What an adventure you
         thought while yawning into the night sky. Luckily you met this girl
         yesterday and you''ve been chatting for hours on the phone. Time flew by,
         in fact you didn''t notice that you''re battery has been drained minutes
         ago...'
    );

-- shadowrun character headers
insert into CharacterHeader (fk_campaign_id, creation_date_millis, edit_date_millis, first_name, character_alias, last_name, race, gender, small_avatar_image_type, small_avatar, character_role, state)
    values (
        '1',
        '1376420869000', -- 08/13/2013 21:07:49  -- x -> y -> z (z = this)
        '1376584344000', -- 08/15/2013 18:32:24 -- z -> y -> x (z = this)
        'James',
        'Inferno',
        'Henders',
        'Orc',
        '0', -- male
        '',
        '', -- we test our image blobs already in step_01 (campaigns)
        'Cleaner',
        'Amped Up'
    );

insert into CharacterBody (fk_character_header_id, avatar_image_type, avatar, age, looks, background, miscellaneous)
    values (
        '1',
        '',
        '',
        '132',
        'Surely not a freshling anymore, Inferno''s Body shows signs of battle. His
        orc tooth are chipped off, his skin is like leather and there is a nasty
        scar right above his left eye. And his voice sounds very much like whisky
        and cigars.',
        'Growing up in the slums of seattle Inferno learned early on to keep his place. In
        fact he has an impressive age for an orc, and that''s not only said regarding the
        risk of the job. Orcs usually just don''t grow that old. He never avoided work
        and he''s not easily disgusted. So he decided to profit of the misery of his part
        of the city and took a profession which wouldn''t run out of work: whenever one
        of his customors need to clean, I mean really clean a place, so it would look
        like nothing ever happened: You got your man right here.',
        'He took on a rather expensive hobby, smoking cigars. Which usually might be about
        the only sign he leaves behind: a bit of smoky air.'
    );

insert into CharacterHeader (fk_campaign_id, creation_date_millis, edit_date_millis, first_name, character_alias, last_name, race, gender, small_avatar_image_type, small_avatar, character_role, state)
    values (
        '1',
        '1375543872000', -- 08/03/2013 17:31:12  -- x -> y -> z (y = this)
        '1376727312000', -- 08/17/2013 10:15:12 -- z -> y -> x (y = this)
        'Amy',
        'Moonlight',
        'Sanders',
        'Human',
        '1', -- female
        '',
        '', -- we test our image blobs already in step_01 (campaigns)
        'Technomancer',
        'Tired'
    );

insert into CharacterBody (fk_character_header_id, avatar_image_type, avatar, age, looks, background, miscellaneous)
    values (
        '2',
        '',
        '',
        '21',
        'She is not one to draw all to much attention, never was. In fact she learned early
        on that it is not vise to show of her natural talent. At least not in the physical
        space. So all you will see from the outside is a quite introverted girl with a
        strange sense of humor and not all to much which would give away any clues about
        her',
        'Growing up with rather rich parents she luckily always had some resources available
        to hide her true Identity. For obvoius reasons being a technomancer determined her
        life in as much good as bad ways. Sure, she was a natural with tech and had it easy
        to pick up jobs, legal ones that is for starters. But her true nature was forced
        into hiding and that''s pretty much what she couldn''t take. So she more and more
        drifted into the shadows and started to pick on that kind of job where people
        would not condemn her talents, but in fact embrace them.',
        'Moonlight''s younger brother and her parents are besides some runners she had
        worked with about the only pepole who really know why she is so tech savy. For
        obvious reasons she very much tries to keep it that way.'
    );

insert into CharacterHeader (fk_campaign_id, creation_date_millis, edit_date_millis, first_name, character_alias, last_name, race, gender, small_avatar_image_type, small_avatar, character_role, state)
    values (
        '1',
        '1376026371000', -- 08/09/2013 07:32:51 --  x -> y -> z (x = this)
        '1376757737000', -- 08/17/2013 18:42:17 -- z -> y -> x (x = this)
        'Xylocs',
        '', -- intentionally left empty
        '', -- intentionally left empty
        'Sprite',
        '2', -- other
        '',
        '', -- we test our image blobs already in step_01 (campaigns)
        'Antagonist',
        'Plotting'
    );

insert into CharacterBody (fk_character_header_id, avatar_image_type, avatar, age, looks, background, miscellaneous)
    values (
        '3',
        '',
        '',
        '205',
        'It only exists within the cyberspace and due to it''s nature it can take on
        many forms. Only for pepole who are really into tech it might be possible to
        even register it''s presence, if it chooses to hide. That said, for some reason
        though it often takes on the form of dogs.',
        'Xylocs is a former technomancer sprite gone rogue. For some reason unknown
        it''s existence not only became permanent, but also indipendent and conscious.
        And due to it''s nature it becomes a real Threat when someone crosses whatever
        it claims as it''s domain.',
        'For some reason it has a tendency to pester pepole it feels attached to.'
    );

-- DnD chars
insert into CharacterHeader (fk_campaign_id, creation_date_millis, edit_date_millis, first_name, character_alias, last_name, race, gender, small_avatar_image_type, small_avatar, character_role, state)
    values (
        '2',
        '1276845714000', -- 06/18/2010 09:21:54 -- x -> y -> z (z = this)
        '1276996341000', -- 06/20/2010 03:12:21 -- z -> y -> x (z = this)
        'Jorik',
        'The Wanderer',
        'Anders',
        'Half-Orc',
        '0', -- male
        '',
        '', -- we test our image blobs already in step_01 (campaigns)
        'Troublemaker',
        'Smashing Stuff'
    );

insert into CharacterBody (fk_character_header_id, avatar_image_type, avatar, age, looks, background, miscellaneous)
    values (
        '4',
        '',
        '',
        '87',
        'When taking a look at Jorik you can easily see that the years are taking it''s
        toll. His former green skin is now more of a grey-ish and carries a lot of old
        scars. His voice sounds somehow rough. Nonetheless his eyes are having an aura
        of "I''ve seen it all". And what certainly never left him is his strength, there
        are muscles all over.',
        'He grew up in a small village, working in a brewery. On a stormy night some
        wanderers took shelter. As it turned out there were a bunch of bandits. After
        continiously trying to humiliate him and his boss, the inn-keeper the situation
        escelated. He left the inn as the only survivor that night, knowing that for his
        skin no one would believe him. So he never looked back and took on the life of
        a sellsword.',
        'He is, in fact, despite his origins a softy. Jorik deeply cares for the pepole
        around him. He is known to take on jobs for next to nothing if he sees that
        his peers, the lower end of society, are opressed or in danger.'
    );

insert into CharacterHeader (fk_campaign_id, creation_date_millis, edit_date_millis, first_name, character_alias, last_name, race, gender, small_avatar_image_type, small_avatar, character_role, state)
    values (
        '2',
        '1275237737000', -- 05/30/2010 18:42:17 -- x -> y -> z (y = this)
        '1277114731000', -- 06/21/2010 12:05:31 -- z -> y -> x (y = this)
        'Leanee',
        'The Siren',
        'Lowshire',
        'Human',
        '1', -- female
        '',
        '', -- we test our image blobs already in step_01 (campaigns)
        'Sorceress',
        'Drunk as fuck'
    );

insert into CharacterBody (fk_character_header_id, avatar_image_type, avatar, age, looks, background, miscellaneous)
    values (
        '5',
        '',
        '',
        '24',
        'Despite the fact that she does not give a shit about clothing or make-up she
        always attracted a lot of pepole around her. Nature certainly blessed her with
        a certain attractiveness, but that is not even it. Her voice is enchanting and
        you rarely find her in a grumpy mood.',
        'She is also a inhabitant of the slums, although she got somehow still lucky. Her
        parents were working there, feeding the hungry for next to nothing. Therefore she
        and her family were more or less saints in the eyes of her peers. Nobody would
        touch them, more the opposite actually: They were always protected, kept company
        and looked after in any imagineable way. One day though she received a latter
        from a childhood friend. It said next to nothing when reading it in the first
        place, but why would she even write? Taking a more close look she realised that
        it was a cipher: Her friend was in dire need of help. So she left early on the
        next day and since than tries to go furher south, looking for her.',
        'Graduating from the academy never took much of a toll on her. In fact she was
        2nd best of her year without even trying. As it seems she has a nearly perfect
        memory and has grown to be something like a human enceclopedia.'
    );

insert into CharacterHeader (fk_campaign_id, creation_date_millis, edit_date_millis, first_name, character_alias, last_name, race, gender, small_avatar_image_type, small_avatar, character_role, state)
    values (
        '2',
        '1274703163000', -- 05/24/2010 14:12:43 --  x -> y -> z (x = this)
        '1289246564000', -- 11/08/2010 21:02:44 -- z -> y -> x (x = this)
        'Rakliz',
        'Stone',
        'Xylastz',
        'Golem',
        '2', -- other
        '',
        '', -- we test our image blobs already in step_01 (campaigns)
        'Guardian',
        'Guarding Stuff'
    );

insert into CharacterBody (fk_character_header_id, avatar_image_type, avatar, age, looks, background, miscellaneous)
    values (
        '6',
        '',
        '',
        '235',
        'He is a classic stone golem. Not much of unique features, he is a bunch of faingly
        humen looking rocks. The only unique mark is a scar on his forehead, lefovers of a
        strike with an imbued weapon.',
        'Commanded by his master he spend the first 179 years of his life guarding a
        tower, not realizing that his master died in that building about a hundred years
        ago. When finally some adventurers stumbled by to, well, retrieve items from
        that building they made some sounds while sneeking by. Rushing into it to
        save his master, he found what was left of him. Now, without a task to fullfill,
        he waited about 20 more years, thinking about what to do. Then he finally
        decided to leave, not sure what to make of his unbound existence.',
        'Not really realizing how much worth that might be, he carries a lot of secrets
        from his former master around.'
    );


-- hunter chars
insert into CharacterHeader (fk_campaign_id, creation_date_millis, edit_date_millis, first_name, character_alias, last_name, race, gender, small_avatar_image_type, small_avatar, character_role, state)
    values (
        '3',
        '1405659272000', -- 07/18/2014 06:54:32 -- x -> y -> z (z = this)
        '1412090475000', -- 09/30/2014 17:21:15 -- z -> y -> x (z = this)
        'James',
        'Wizard',
        'Masterson',
        'Human',
        '0', -- male
        '',
        '', -- we test our image blobs already in step_01 (campaigns)
        'Hacker',
        'Asleep'
    );

insert into CharacterBody (fk_character_header_id, avatar_image_type, avatar, age, looks, background, miscellaneous)
    values (
        '7',
        '',
        '',
        '28',
        'Masterson is a classic stereotype. He is a bit overweight, and grows a lot of
        hair on his head and in his face. His voice is rather soft though and
        somehow unfitting to his presence. Usually he wears some band shirt where you
        cannot make out the name and everything black.',
        'He never finised his studies, in fact he put a lot of energy into extending
        his stay at the university till up to today. No end in sight yet. His life
        changed a lot though when he "explored" contents within a hidden network at
        the university. That''s when he accidentially broke into a network of hunters,
        leaving traces. So he got visitors the next day, which explained to him his
        options. They were not pleased and his only way to escape unharmed was to
        actually join their operation. Since then he has become a valuable asset to
        the society. Well, the hidden one at least.',
        'Publishing content from early on he has actually more influence than he
        realises within the digital sphere. So there is a bunch of people he can
        count on when starting a bigger operation.'
    );


insert into CharacterHeader (fk_campaign_id, creation_date_millis, edit_date_millis, first_name, character_alias, last_name, race, gender, small_avatar_image_type, small_avatar, character_role, state)
    values (
        '3',
        '1404062094000', -- 06/29/2014 19:14:54 -- x -> y -> z (y = this)
        '1415187342000', -- 11/05/2014 12:35:42 -- z -> y -> x (y = this)
        'Amy',
        '', -- intentionally left blank
        'Maverik',
        'Human',
        '1', -- female
        '',
        '', -- we test our image blobs already in step_01 (campaigns)
        'Scout',
        'Wounded'
    );

insert into CharacterBody (fk_character_header_id, avatar_image_type, avatar, age, looks, background, miscellaneous)
    values (
        '8',
        '',
        '',
        '21',
        'Amy could easily pull of a prom queen look whenever she wanted to, But that has
        never been her style, in fact she prefers to keep things more natural. Her hair
        is reached down below her shoulders and is usually bound together, she rarely
        wears make-up and would almost never wear a skirt or anything like it. Still,
        she usually draws the attention of people around her.',
        'She always was into sports, so she turned her hobby into a profession and became
        a personal trainer. The fact that she was drawn into this whole other world was
        thanks to her boyfriend at he time being. He was in the society for a long time
        and could not really hide all those times he had to sneak out in the middle of
        the night. So she snuck out as well, following him. That night she was able to
        save his life and the life of some of his peers by intervening when fighting
        a siren. Having seen what lurks in the shadows, she tried to let go at first
        but ultimatively her world changed that day forever.',
        ''
    );


insert into CharacterHeader (fk_campaign_id, creation_date_millis, edit_date_millis, first_name, character_alias, last_name, race, gender, small_avatar_image_type, small_avatar, character_role, state)
    values (
        '3',
        '1395477307000', -- 03/22/2014 09:35:07 --  x -> y -> z (x = this)
        '1419440400000', -- 12/24/2014 18:00:00-- z -> y -> x (x = this)
        'Cat',
        'Whispers',
        'Melody',
        'Spirit',
        '2', -- other
        '',
        '', -- we test our image blobs already in step_01 (campaigns)
        'Spirit Animal',
        'Fooling around'
    );

insert into CharacterBody (fk_character_header_id, avatar_image_type, avatar, age, looks, background, miscellaneous)
    values (
        '9',
        '',
        '',
        '307',
        'Looks like a black cat, with one yellow and one blue eye. It could eat a
        bit less though.',
        'Whispers is an old spirit animal which usually fools aroudn with mortals while,
        well, being cuddled most of the time. It usually tries not to give away it''s
        true nature but it also hates everything of infernal origin which sometimes
        forces it to act.',
        'Despite it''s true nature, Whispers loves fish just like every other cat and is
        easily persuaded that way.'
    );
