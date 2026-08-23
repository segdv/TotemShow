execute as @a[scores={segtotems=1..}] run tellraw @a {"translate":"segtotems.message","fallback":"§6☠ %s §7used a §eTotem of Undying§6!","with":[{"selector":"@s"}]}
execute as @a[scores={segtotems=1..}] run scoreboard players remove @s segtotems 1

schedule function segtotems:loop 4t
