/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.referencemetadata;

import org.openmrs.Concept;
import org.openmrs.OrderFrequency;
import org.openmrs.api.context.Context;
import org.openmrs.module.metadatadeploy.bundle.AbstractMetadataBundle;
import org.springframework.stereotype.Component;

/**
 * This class adds the best practice order frequencies into the order_frequency database table
 */
@Component("referenceApplicationOrderFrequencies")
public class OrderFrequencyMetadata extends AbstractMetadataBundle {

	/**
	 * This creates the orderFrequency object needed to populate the order_frequency table
	 */
	public static OrderFrequency orderFrequency(String conceptId, String uuid, Double frequencyPerDay) {
		Concept concept = Context.getConceptService().getConcept(conceptId);
		OrderFrequency obj = new OrderFrequency();

		obj.setConcept(concept);
		obj.setUuid(uuid);
		obj.setFrequencyPerDay(frequencyPerDay);

		return obj;
	}

	@Override
	public void install() throws Exception {
		install(orderFrequency(Concepts.ONCE, OrderFrequencies.ONCE, 1.0));
		install(orderFrequency(Concepts.EVERY_30_MINS, OrderFrequencies.EVERY_30_MINS, 48.0));
		install(orderFrequency(Concepts.EVERY_HOUR, OrderFrequencies.EVERY_HOUR, 24.0));
		install(orderFrequency(Concepts.EVERY_TWO_HOURS, OrderFrequencies.EVERY_TWO_HOURS, 12.0));
		install(orderFrequency(Concepts.EVERY_THREE_HOURS, OrderFrequencies.EVERY_THREE_HOURS, 8.0));
		install(orderFrequency(Concepts.EVERY_FOUR_HOURS, OrderFrequencies.EVERY_FOUR_HOURS, 6.0));
		install(orderFrequency(Concepts.EVERY_FIVE_HOURS, OrderFrequencies.EVERY_FIVE_HOURS, 4.8));
		install(orderFrequency(Concepts.EVERY_SIX_HOURS, OrderFrequencies.EVERY_SIX_HOURS, 4.0));
		install(orderFrequency(Concepts.EVERY_EIGHT_HOURS, OrderFrequencies.EVERY_EIGHT_HOURS, 3.0));
		install(orderFrequency(Concepts.EVERY_TWELVE_HOURS, OrderFrequencies.EVERY_TWELVE_HOURS, 2.0));
		install(orderFrequency(Concepts.TWICE_DAILY, OrderFrequencies.TWICE_DAILY, 2.0));
		install(orderFrequency(Concepts.TWICE_DAILY_BEFORE_MEALS, OrderFrequencies.TWICE_DAILY_BEFORE_MEALS, 2.0));
		install(orderFrequency(Concepts.TWICE_DAILY_AFTER_MEALS, OrderFrequencies.TWICE_DAILY_AFTER_MEALS, 2.0));
		install(orderFrequency(Concepts.TWICE_DAILY_WITH_MEALS, OrderFrequencies.TWICE_DAILY_WITH_MEALS, 2.0));
		install(orderFrequency(Concepts.EVERY_24_HOURS, OrderFrequencies.EVERY_24_HOURS, 1.0));
		install(orderFrequency(Concepts.ONCE_DAILY, OrderFrequencies.ONCE_DAILY, 1.0));
		install(orderFrequency(Concepts.ONCE_DAILY_BEDTIME, OrderFrequencies.ONCE_DAILY_BEDTIME, 1.0));
		install(orderFrequency(Concepts.ONCE_DAILY_EVENING, OrderFrequencies.ONCE_DAILY_EVENING, 1.0));
		install(orderFrequency(Concepts.ONCE_DAILY_MORNING, OrderFrequencies.ONCE_DAILY_MORNING, 1.0));
		install(orderFrequency(Concepts.THRICE_DAILY, OrderFrequencies.THRICE_DAILY, 3.0));
		install(orderFrequency(Concepts.THRICE_DAILY_AFTER_MEALS, OrderFrequencies.THRICE_DAILY_AFTER_MEALS, 3.0));
		install(orderFrequency(Concepts.THRICE_DAILY_BEFORE_MEALS, OrderFrequencies.THRICE_DAILY_BEFORE_MEALS, 3.0));
		install(orderFrequency(Concepts.THRICE_DAILY_WITH_MEALS, OrderFrequencies.THRICE_DAILY_WITH_MEALS, 3.0));
		install(orderFrequency(Concepts.FOUR_TIMES__DAILY_WITH_MEALS, OrderFrequencies.FOUR_TIMES__DAILY_WITH_MEALS, 4.0));
		install(orderFrequency(Concepts.FOUR_TIMES__AFTER_WITH_MEALS_BEDTIME,
				OrderFrequencies.FOUR_TIMES__AFTER_WITH_MEALS_BEDTIME, 4.0));
		install(orderFrequency(Concepts.FOUR_TIMES__BEFORE_WITH_MEALS_BEDTIME,
				OrderFrequencies.FOUR_TIMES__BEFORE_WITH_MEALS_BEDTIME, 4.0));
		install(orderFrequency(Concepts.EVERY_48_HOURS, OrderFrequencies.EVERY_48_HOURS, 0.5));
		install(orderFrequency(Concepts.EVERY_36_HOURS, OrderFrequencies.EVERY_36_HOURS, (1 / 36) * 24.0));
		install(orderFrequency(Concepts.EVERY_72_HOURS, OrderFrequencies.EVERY_72_HOURS, (1 / 72) * 24.0));
		install(orderFrequency(Concepts.MONDAY_WEDNESDAY_FRIDAY, OrderFrequencies.MONDAY_WEDNESDAY_FRIDAY, (3 / 168) * 24.0));
	}

	public static class OrderFrequencies {

		public static final String ONCE = "162135OFAAAAAAAAAAAAAAA";

		public static final String TWICE_DAILY = "160858OFAAAAAAAAAAAAAAA";

		public static final String TWICE_DAILY_BEFORE_MEALS = "160859OFAAAAAAAAAAAAAAA";

		public static final String TWICE_DAILY_AFTER_MEALS = "160860OFAAAAAAAAAAAAAAA";

		public static final String TWICE_DAILY_WITH_MEALS = "160861OFAAAAAAAAAAAAAAA";

		public static final String ONCE_DAILY = "160862OFAAAAAAAAAAAAAAA";

		public static final String ONCE_DAILY_BEDTIME = "160863OFAAAAAAAAAAAAAAA";

		public static final String ONCE_DAILY_EVENING = "160864OFAAAAAAAAAAAAAAA";

		public static final String ONCE_DAILY_MORNING = "160865OFAAAAAAAAAAAAAAA";

		public static final String THRICE_DAILY = "160866OFAAAAAAAAAAAAAAA";

		public static final String THRICE_DAILY_AFTER_MEALS = "160867OFAAAAAAAAAAAAAAA";

		public static final String THRICE_DAILY_BEFORE_MEALS = "160868OFAAAAAAAAAAAAAAA";

		public static final String THRICE_DAILY_WITH_MEALS = "160869OFAAAAAAAAAAAAAAA";

		public static final String FOUR_TIMES__DAILY_WITH_MEALS = "160870OFAAAAAAAAAAAAAAA";

		public static final String FOUR_TIMES__AFTER_WITH_MEALS_BEDTIME = "160871OFAAAAAAAAAAAAAAA";

		public static final String FOUR_TIMES__BEFORE_WITH_MEALS_BEDTIME = "160872OFAAAAAAAAAAAAAAA";

		public static final String EVERY_30_MINS = "162243OFAAAAAAAAAAAAAAA";

		public static final String EVERY_HOUR = "162244OFAAAAAAAAAAAAAAA";

		public static final String EVERY_TWO_HOURS = "162245OFAAAAAAAAAAAAAAA";

		public static final String EVERY_THREE_HOURS = "162246OFAAAAAAAAAAAAAAA";

		public static final String EVERY_FOUR_HOURS = "162247OFAAAAAAAAAAAAAAA";

		public static final String EVERY_FIVE_HOURS = "162248OFAAAAAAAAAAAAAAA";

		public static final String EVERY_SIX_HOURS = "162249OFAAAAAAAAAAAAAAA";

		public static final String EVERY_EIGHT_HOURS = "162250OFAAAAAAAAAAAAAAA";

		public static final String EVERY_TWELVE_HOURS = "162251OFAAAAAAAAAAAAAAA";

		public static final String EVERY_24_HOURS = "162252OFAAAAAAAAAAAAAAA";

		public static final String EVERY_48_HOURS = "162253OFAAAAAAAAAAAAAAA";

		public static final String EVERY_36_HOURS = "162254OFAAAAAAAAAAAAAAA";

		public static final String EVERY_72_HOURS = "162255OFAAAAAAAAAAAAAAA";

		public static final String MONDAY_WEDNESDAY_FRIDAY = "162256OFAAAAAAAAAAAAAAA";
	}

	public static class Concepts {

		public static final String ONCE = "162135";

		public static final String TWICE_DAILY = "160858";

		public static final String TWICE_DAILY_BEFORE_MEALS = "160859";

		public static final String TWICE_DAILY_AFTER_MEALS = "160860";

		public static final String TWICE_DAILY_WITH_MEALS = "160861";

		public static final String ONCE_DAILY = "160862";

		public static final String ONCE_DAILY_BEDTIME = "160863";

		public static final String ONCE_DAILY_EVENING = "160864";

		public static final String ONCE_DAILY_MORNING = "160865";

		public static final String THRICE_DAILY = "160866";

		public static final String THRICE_DAILY_AFTER_MEALS = "160867";

		public static final String THRICE_DAILY_BEFORE_MEALS = "160868";

		public static final String THRICE_DAILY_WITH_MEALS = "160869";

		public static final String FOUR_TIMES__DAILY_WITH_MEALS = "160870";

		public static final String FOUR_TIMES__AFTER_WITH_MEALS_BEDTIME = "160871";

		public static final String FOUR_TIMES__BEFORE_WITH_MEALS_BEDTIME = "160872";

		public static final String EVERY_30_MINS = "162243";

		public static final String EVERY_HOUR = "162244";

		public static final String EVERY_TWO_HOURS = "162245";

		public static final String EVERY_THREE_HOURS = "162246";

		public static final String EVERY_FOUR_HOURS = "162247";

		public static final String EVERY_FIVE_HOURS = "162248";

		public static final String EVERY_SIX_HOURS = "162249";

		public static final String EVERY_EIGHT_HOURS = "162250";

		public static final String EVERY_TWELVE_HOURS = "162251";

		public static final String EVERY_24_HOURS = "162252";

		public static final String EVERY_48_HOURS = "162253";

		public static final String EVERY_36_HOURS = "162254";

		public static final String EVERY_72_HOURS = "162255";

		public static final String MONDAY_WEDNESDAY_FRIDAY = "162256";
	}
}
