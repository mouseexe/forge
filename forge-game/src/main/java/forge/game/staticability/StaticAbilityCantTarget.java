/*
 * Forge: Play Magic: the Gathering.
 * Copyright (C) 2011  Forge Team
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package forge.game.staticability;

import forge.game.card.Card;
import forge.game.keyword.KeywordInterface;
import forge.game.player.Player;
import forge.game.spellability.SpellAbility;
import forge.game.zone.ZoneType;

/**
 * The Class StaticAbilityCantTarget.
 */
public class StaticAbilityCantTarget {

    /**
     * Apply can't target ability.
     *
     * @param stAb
     *            the static ability
     * @param card
     *            the card
     * @param spellAbility
     *            the spell/ability
     * @return true, if successful
     */
    public static boolean applyCantTargetAbility(final StaticAbility stAb, final Card card,
            final SpellAbility spellAbility) {
        final Card source = spellAbility.getHostCard();
        final Player activator = spellAbility.getActivatingPlayer();

        if (stAb.hasParam("ValidPlayer")) {
            return false;
        }

        if (stAb.hasParam("AffectedZone")) {
            boolean inZone = false;
            for (final ZoneType zt : ZoneType.listValueOf(stAb.getParam("AffectedZone"))) {
                if (card.isInZone(zt)) {
                    inZone = true;
                    break;
                }
            }

            if (!inZone) {
                return false;
            }
        } else { // default zone is battlefield
            if (!card.isInPlay()) {
                return false;
            }
        }


        if (!stAb.matchesValidParam("ValidCard", card)) {
            return false;
        }


        if (stAb.hasParam("Hexproof") && (activator != null)) {
            for (KeywordInterface kw : activator.getKeywords()) {
                String k = kw.getOriginal();
                if (k.startsWith("IgnoreHexproof")) {
                    String[] m = k.split(":");
                    if (card.isValid(m[1].split(","), activator, source, null)) {
                        return false;
                    }
                }
            }
        }
        if (stAb.hasParam("Shroud") && (activator != null)) {
            for (KeywordInterface kw : activator.getKeywords()) {
                String k = kw.getOriginal();
                if (k.startsWith("IgnoreShroud")) {
                    String[] m = k.split(":");
                    if (card.isValid(m[1].split(","), activator, source, null)) {
                        return false;
                    }
                }
            }
        }

        return common(stAb, spellAbility);
    }

    public static boolean applyCantTargetAbility(final StaticAbility stAb, final Player player,
            final SpellAbility spellAbility) {
        final Card source = spellAbility.getHostCard();
        final Player activator = spellAbility.getActivatingPlayer();

        if (stAb.hasParam("ValidCard") || stAb.hasParam("AffectedZone")) {
            return false;
        }

        if (!stAb.matchesValidParam("ValidPlayer", player)) {
            return false;
        }

        if (stAb.hasParam("Hexproof") && (activator != null)) {
            for (KeywordInterface kw : activator.getKeywords()) {
                String k = kw.getOriginal();
                if (k.startsWith("IgnoreHexproof")) {
                    String[] m = k.split(":");
                    if (player.isValid(m[1].split(","), activator, source, null)) {
                        return false;
                    }
                }
            }
        }

        return common(stAb, spellAbility);
    }

    protected static boolean common(final StaticAbility stAb, final SpellAbility spellAbility) {
        final Card source = spellAbility.getHostCard();
        final Player activator = spellAbility.getActivatingPlayer();

        if (!stAb.matchesValidParam("ValidSA", spellAbility)) {
            return false;
        }

        if (!stAb.matchesValidParam("ValidSource", source)) {
            return false;
        }

        if (!stAb.matchesValidParam("Activator", activator)) {
            return false;
        }

        if (spellAbility.hasParam("ValidTgts") &&
                (stAb.hasParam("SourceCanOnlyTarget")
                && (!spellAbility.getParam("ValidTgts").contains(stAb.getParam("SourceCanOnlyTarget"))
                    || spellAbility.getParam("ValidTgts").contains(","))
                    || spellAbility.getParam("ValidTgts").contains("non" + stAb.getParam("SourceCanOnlyTarget")
                    )
                )
           ){
            return false;
        }

        return true;
    }
}
