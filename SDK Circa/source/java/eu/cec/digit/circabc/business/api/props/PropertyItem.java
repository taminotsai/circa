package eu.cec.digit.circabc.business.api.props;

import java.io.Serializable;


/**
 * Wrap a node property with the property itself (name and value), the property configuration (readonly, ...) and allow edition.
 *
 * @author Yanick Pignot
 */
public interface PropertyItem
{

	/**
	 * The unique identifier of a property
	 *
	 * @return the name
	 */
	public abstract String getName();

	/**
	 * The current value of the property. It means that the method returns the updated value if client changet it.
	 *
	 * @return the value
	 */
	public abstract Serializable getValue();

	/**
	 * The original value of the property (can be null)
	 *
	 * @return the value
	 */
	public abstract Serializable getOriginalValue();

	/**
	 * The updated value of the property (can be null)
	 *
	 * @return the value
	 */
	public abstract Serializable getUpdatedValue();

	/**
	 * Update the value of the property (null allowed).
	 *
	 * <b>It is the responsability of the services to check and validate the property value and RW mode.</b>
	 *
	 * @return the value
	 */
	public abstract void setUpdatedValue(final Serializable newValue) ;

	/**
	 * Return if the property has been updated
	 *
	 * @return true if the value is updated
	 */
	public abstract boolean isValueUpdated();

	/**
	 * The dispaly label of the property
	 *
	 * @return the label
	 */
	public abstract String getLabel();

	/**
	 * Is the property in read only mode (the property will be displayed in its edition process)
	 *
	 * @return the readOnly
	 */
	public abstract boolean isReadOnly();

	/**
	 * Is the property must be displayed even if it is not set or not part this kind of node.
	 *
	 * @return the ignoreIfMissing
	 */
	public abstract boolean isIgnoreIfMissing();

	/**
	 * Is the property must be displayed in editing mode?
	 *
	 * @return the viewInEditMode
	 */
	public abstract boolean isViewInEditMode();

	/**
	 * Is the property must be displayed in view mode?
	 *
	 * @return the viewInViewMode
	 */
	public abstract boolean isViewInViewMode();

	/**
	 * The conveter configuration identifier used by the target client to display the property.
	 *
	 * <b>It's the responsability of the client to know and interpret this value according its needs</b>
	 *
	 * @return the readConverter
	 */
	public abstract String getReadConverter();

	/**
	 * The conveter configuration identifier used by the target client to edit the property.
	 *
	 * <b>It's the responsability of the client to know and interpret this value according its needs</b>
	 *
	 * @return the writeConverter
	 */
	public abstract String getWriteConverter();

}