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
package forge.card.trigger;

import java.util.Map;

import forge.Card;
import forge.card.spellability.SpellAbility;

/**
 * <p>
 * Trigger_Always class.
 * </p>
 * 
 * @author Forge
 * @version $Id$
 */
public class TriggerAlways extends Trigger {

    /**
     * <p>
     * Constructor for Trigger_Always.
     * </p>
     * 
     * @param params
     *            a {@link java.util.HashMap} object.
     * @param host
     *            a {@link forge.Card} object.
     * @param intrinsic
     *            the intrinsic
     */
    public TriggerAlways(final java.util.Map<String, String> params, final Card host, final boolean intrinsic) {
        super(params, host, intrinsic);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean performTest(final Map<String, Object> runParams2) {
        return true;
    }

    /** {@inheritDoc} */
    @Override
    public final Trigger getCopy() {
        final Trigger copy = new TriggerAlways(this.getMapParams(), this.getHostCard(), this.isIntrinsic());
        if (this.getOverridingAbility() != null) {
            copy.setOverridingAbility(this.getOverridingAbility());
        }

        copyFieldsTo(copy);
        return copy;
    }

    /** {@inheritDoc} */
    @Override
    public void setTriggeringObjects(final SpellAbility sa) {
    }
}
