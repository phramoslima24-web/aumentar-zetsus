# Narutoto Spawn & Reward Addon

Addon independente para **Minecraft 1.20.1 Forge 47.x**, feito para trabalhar com **NarutoTo 0.4.1**.

## Configuração incluída
- Spawn aumentado de Genins, Chunins, Jonins e Bandidos no Overworld.
- Spawn aumentado de Zetsu Branco e ninjas renegados no Overworld.
- Zetsu Branco é configurado como mob hostil (a regra natural de spawn noturno do próprio NarutoTo continua valendo).
- Ao derrotar um ninja renegado (`missing_nin`, `missing_nin_wild` ou `missing_nin_camp`) como jogador: **+3.000 EX, +200 SP e +100 JP**.
- As variantes de Genin/Chunin/Jonin usam as entidades originais das cinco vilas do NarutoTo.

## Dependências
- Minecraft 1.20.1
- Forge 47.x
- NarutoTo 0.4.1 (obrigatório)

## Compilar
`gradle build`

O GitHub Actions compila automaticamente ao enviar o projeto para um repositório GitHub. O JAR estará em `build/libs/` e também como artefato da execução do workflow.

## Instalação
Coloque o JAR deste addon em `mods/` junto com Forge 1.20.1 e NarutoTo 0.4.1.

## Observações técnicas
As recompensas acessam por reflexão os campos públicos da capability do NarutoTo (`ninja_experience`, `attribute_point`, `jutsu_point`) e sincronizam as variáveis do jogador. Isso evita embutir ou redistribuir o JAR do NarutoTo no projeto.
