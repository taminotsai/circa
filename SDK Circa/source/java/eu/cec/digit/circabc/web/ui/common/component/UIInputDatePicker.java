/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.web.ui.common.component;

import java.util.Date;

import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * Class for setter and getter
 *
 * @author Guillaume
 */
public class UIInputDatePicker extends UIComponentBase
{
	// ------------------------------------------------------------------------------
	// Construction

	/**
	 * Default Constructor
	 */
	public UIInputDatePicker()
	{
		setRendererType("eu.cec.digit.circabc.faces.DatePickerRenderer");
	}

	// ------------------------------------------------------------------------------
	// Component implementation

	/**
	 * @see javax.faces.component.UIComponent#getFamily()
	 */
	public String getFamily()
	{
		return "eu.cec.digit.circabc.faces.Input";
	}

	/**
	 * @see javax.faces.component.StateHolder#restoreState(javax.faces.context.FacesContext, java.lang.Object)
	 */
	public void restoreState(FacesContext context, Object state)
	{
		Object values[] = (Object[]) state;
		// standard component attributes are restored by the super class
		super.restoreState(context, values[0]);
		this.startYear = (Integer) values[1];
		this.yearCount = (Integer) values[2];
		this.value = (Date) values[3];
		this.showTime = (Boolean) values[4];
		this.disabled = (Boolean) values[5];
		this.initialiseIfNull = (Boolean) values[6];
		this.noneLabel = (String) values[7];
		this.alreadyRead = ((Boolean) values[8]).booleanValue();
		this.showDate = (Boolean) values[9];
		this.timeAsList = (Boolean) values[10];
		this.step = (Integer) values[11];
	}

	/**
	 * @see javax.faces.component.StateHolder#saveState(javax.faces.context.FacesContext)
	 */
	public Object saveState(FacesContext context)
	{
		Object values[] = new Object[]
		{
			// standard component attributes are saved by the super class
			super.saveState(context),
			this.startYear,
			this.yearCount,
			this.value,
			this.showTime,
			this.disabled,
			this.initialiseIfNull,
			this.noneLabel,
			this.alreadyRead,
			this.showDate,
			this.timeAsList,
			this.step
		};
		return (values);
	}

	// ------------------------------------------------------------------------------
	// Strongly typed component property accessors

	/**
	 * Get start year for the year's drop down
	 *
	 * @return Start year for the year's drop down
	 */
	public int getStartYear()
	{
		ValueBinding vb = getValueBinding("startYear");
		if (vb != null)
		{
			this.startYear = ((Integer) vb.getValue(getFacesContext())).intValue();
		}

		return this.startYear;
	}

	/**
	 * Set start year for the year's drop down
	 *
	 * @param startYear Start year for the year's drop down
	 */
	public void setStartYear(int startYear)
	{
		this.startYear = startYear;
	}

	/**
	 * Get number of element in the year's the drop down
	 *
	 * @return Number of element in the year's the drop down
	 */
	public int getYearCount()
	{
		ValueBinding vb = getValueBinding("yearCount");
		if (vb != null)
		{
			this.yearCount = ((Integer) vb.getValue(getFacesContext())).intValue();
		}

		return this.yearCount;
	}

	/**
	 * Set number of element in the year's the drop down
	 *
	 * @param yearCount Number of element in the year's the drop down
	 */
	public void setYearCount(int yearCount)
	{
		this.yearCount = yearCount;
	}

	/**
	 * Get value of the date
	 *
	 * @return Value of the date
	 */
	public Date getValue()
	{
		if (!this.alreadyRead) {
			// We don't get the value from the bean
			ValueBinding vb = getValueBinding("value");
			if (vb != null)
			{
				this.value = ((Date) vb.getValue(getFacesContext()));
			}
			this.alreadyRead = true;
		}


		return this.value;
	}

	/**
	 * Set value of the date
	 *
	 * @param value Value of the date
	 */
	public void setValue(Date value)
	{
		this.value = value;
	}

	/**
	 * Set value of the date (only value binding)
	 *
	 * @param value Value of the date (only value binding)
	 */
	public void setValueFinal(Date value)
	{
		ValueBinding vb = getValueBinding("value");
        if (vb == null) return;
        vb.setValue(getFacesContext(), this.value);
	}

	/**
	 * Get if should display choice for minute and hour
	 *
	 * @return should display choice for minute and hour ?
	 */
	public boolean getShowTime()
	{
		ValueBinding vb = getValueBinding("showTime");
		if (vb != null)
		{
			this.showTime = (Boolean) vb.getValue(getFacesContext());
		}

		if (this.showTime != null)
		{
			return this.showTime.booleanValue();
		} else
		{
			// return default
			return false;
		}
	}

	/**
	 * Get if should display choice for day, month and year
	 *
	 * @return should display choice for day, month and year ?
	 */
	public boolean getShowDate()
	{
		ValueBinding vb = getValueBinding("showDate");
		if (vb != null)
		{
			this.showDate = (Boolean) vb.getValue(getFacesContext());
		}

		if (this.showDate != null)
		{
			return this.showDate.booleanValue();
		}
		else
		{
			// return default
			return true;
		}
	}

	/**
	 * Set if true the component will display choice for day, month and year
	 *
	 * @param showTime If true the component will display choice for minute and hour
	 */
	public void setShowDate(boolean showDate)
	{
		this.showDate = showDate;
	}

	/**
	 * Get if should display the time as a select list ot not
	 *
	 * @return should display the time as a select list ?
	 */
	public boolean getTimeAsList()
	{
		ValueBinding vb = getValueBinding("timeAsList");
		if (vb != null)
		{
			this.timeAsList = (Boolean) vb.getValue(getFacesContext());
		}

		if (this.timeAsList != null)
		{
			return this.timeAsList.booleanValue();
		}
		else
		{
			// return default
			return false;
		}
	}

	/**
	 * Set if true the component will isplay the time as a select list
	 *
	 * @param showTime If true the component will isplay the time as a select list
	 */
	public void setTimeAsList(boolean timeAsList)
	{
		this.timeAsList = timeAsList;
	}

	/**
	 * Set if true the component will display choice for minute and hour
	 *
	 * @param showTime If true the component will display choice for minute and hour
	 */
	public void setShowTime(boolean showTime)
	{
		this.showTime = showTime;
	}

	/**
	 * Get if component should be disabled
	 *
	 * @return should component be disabled ?
	 */
	public boolean getDisabled()
	{
		ValueBinding vb = getValueBinding("disabled");
		if (vb != null)
		{
			this.disabled = (Boolean) vb.getValue(getFacesContext());
		}

		if (this.disabled != null)
		{
			return this.disabled.booleanValue();
		} else
		{
			// return default
			return false;
		}
	}

	/**
	 * Set if true the component will be disabled
	 *
	 * @param disabled If true the component will be disabled
	 */
	public void setDisabled(boolean disabled)
	{
		this.disabled = Boolean.valueOf(disabled);
	}

	/**
	 * Get the date should be initialized (else null)
	 *
	 * @return should date be initialized ?
	 */
	public boolean getInitialiseIfNull()
	{
		ValueBinding vb = getValueBinding("initialiseIfNull");
		if (vb != null)
		{
			this.initialiseIfNull = (Boolean) vb.getValue(getFacesContext());
		}

		if (this.initialiseIfNull != null)
		{
			return this.initialiseIfNull.booleanValue();
		}
		else
		{
			// return default
			return false;
		}
	}

	/**
	 * Set if the date should be initialized (else null)
	 *
	 * @param initIfNull If the date should be initialized (else null)
	 */
	public void setInitialiseIfNull(boolean initialiseIfNull)
	{
		this.initialiseIfNull = Boolean.valueOf(initialiseIfNull);
	}

	/**
	 * Get default text is the date is null
	 *
	 * @return Default text is the date is null
	 */
	public String getNoneLabel()
	{
		ValueBinding vb = getValueBinding("noneLabel");
		if (vb != null)
		{
			this.noneLabel = (String) vb.getValue(getFacesContext());
		}

		return this.noneLabel;
	}

	/**
	 * Set default text is the date is null
	 *
	 * @param value default text is the date is null
	 */
	public void setNoneLabel(String noneLabel)
	{
		this.noneLabel = noneLabel;
	}



	// ------------------------------------------------------------------------------
	// Private data

	public void setStep(int step)
	{
		this.step = step;
	}

	public int getStep()
	{
		ValueBinding vb = getValueBinding("step");
		if (vb != null)
		{
			this.step = ((Integer) vb.getValue(getFacesContext())).intValue();
		}

		return this.step;
	}



	/** The start year for the year's drop down */
	private int startYear;

	/** The number of element in the year's the drop down */
	private int yearCount = 25;

	/** The value of the date */
	private Date value = null;

	/** If true the component will display choice for minute and hour */
	private Boolean showTime;

	/** If true the component will be disabled */
	private Boolean disabled;

	/** If true the date will be initialized (else null) */
	private Boolean initialiseIfNull;

	/** The default text is the date is null */
	private String noneLabel = null;

	/** Tell if we already have done the value binding for the value attribute - To keep the value set by the form */
	private boolean alreadyRead = false;

	/** If false the component will not display choice for day, month and year */
	private Boolean showDate;

	/** if true, the date will be displayed as a list with x min of interval*/
	private Boolean timeAsList;
	
	/** step in minutes*/
	private int step =15;

}
