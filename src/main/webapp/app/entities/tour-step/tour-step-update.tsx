import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { ITour } from 'app/shared/model/tour.model';
import { getEntities as getTours } from 'app/entities/tour/tour.reducer';
import { IPlace } from 'app/shared/model/place.model';
import { getEntities as getPlaces } from 'app/entities/place/place.reducer';
import { ITourStep } from 'app/shared/model/tour-step.model';
import { DurationMeasure } from 'app/shared/model/enumerations/duration-measure.model';
import { getEntity, updateEntity, createEntity, reset } from './tour-step.reducer';

export const TourStepUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const users = useAppSelector(state => state.userManagement.users);
  const tours = useAppSelector(state => state.tour.entities);
  const places = useAppSelector(state => state.place.entities);
  const tourStepEntity = useAppSelector(state => state.tourStep.entity);
  const loading = useAppSelector(state => state.tourStep.loading);
  const updating = useAppSelector(state => state.tourStep.updating);
  const updateSuccess = useAppSelector(state => state.tourStep.updateSuccess);
  const durationMeasureValues = Object.keys(DurationMeasure);

  const handleClose = () => {
    navigate('/tour-step' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getUsers({}));
    dispatch(getTours({}));
    dispatch(getPlaces({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }
    if (values.stepOrder !== undefined && typeof values.stepOrder !== 'number') {
      values.stepOrder = Number(values.stepOrder);
    }
    if (values.waitTime !== undefined && typeof values.waitTime !== 'number') {
      values.waitTime = Number(values.waitTime);
    }
    if (values.driveTime !== undefined && typeof values.driveTime !== 'number') {
      values.driveTime = Number(values.driveTime);
    }

    const entity = {
      ...tourStepEntity,
      ...values,
      createdBy: users.find(it => it.id.toString() === values.createdBy?.toString()),
      tour: tours.find(it => it.id.toString() === values.tour?.toString()),
      place: places.find(it => it.id.toString() === values.place?.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          waitTimeMeasure: 'MINUTES',
          driveTimeMeasure: 'MINUTES',
          ...tourStepEntity,
          createdBy: tourStepEntity?.createdBy?.id,
          tour: tourStepEntity?.tour?.id,
          place: tourStepEntity?.place?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="xploraAdminApp.tourStep.home.createOrEditLabel" data-cy="TourStepCreateUpdateHeading">
            <Translate contentKey="xploraAdminApp.tourStep.home.createOrEditLabel">Create or edit a TourStep</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="tour-step-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('xploraAdminApp.tourStep.code')}
                id="tour-step-code"
                name="code"
                data-cy="code"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('xploraAdminApp.tourStep.enabled')}
                id="tour-step-enabled"
                name="enabled"
                data-cy="enabled"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('xploraAdminApp.tourStep.icon')}
                id="tour-step-icon"
                name="icon"
                data-cy="icon"
                type="text"
              />
              <ValidatedField
                label={translate('xploraAdminApp.tourStep.stepOrder')}
                id="tour-step-stepOrder"
                name="stepOrder"
                data-cy="stepOrder"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('xploraAdminApp.tourStep.waitTime')}
                id="tour-step-waitTime"
                name="waitTime"
                data-cy="waitTime"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('xploraAdminApp.tourStep.waitTimeMeasure')}
                id="tour-step-waitTimeMeasure"
                name="waitTimeMeasure"
                data-cy="waitTimeMeasure"
                type="select"
              >
                {durationMeasureValues.map(durationMeasure => (
                  <option value={durationMeasure} key={durationMeasure}>
                    {translate('xploraAdminApp.DurationMeasure.' + durationMeasure)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('xploraAdminApp.tourStep.driveTime')}
                id="tour-step-driveTime"
                name="driveTime"
                data-cy="driveTime"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('xploraAdminApp.tourStep.driveTimeMeasure')}
                id="tour-step-driveTimeMeasure"
                name="driveTimeMeasure"
                data-cy="driveTimeMeasure"
                type="select"
              >
                {durationMeasureValues.map(durationMeasure => (
                  <option value={durationMeasure} key={durationMeasure}>
                    {translate('xploraAdminApp.DurationMeasure.' + durationMeasure)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('xploraAdminApp.tourStep.createdDate')}
                id="tour-step-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="date"
              />
              <ValidatedField
                id="tour-step-createdBy"
                name="createdBy"
                data-cy="createdBy"
                label={translate('xploraAdminApp.tourStep.createdBy')}
                type="select"
              >
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.login}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="tour-step-tour"
                name="tour"
                data-cy="tour"
                label={translate('xploraAdminApp.tourStep.tour')}
                type="select"
                required
              >
                <option value="" key="0" />
                {tours
                  ? tours.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.code}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <ValidatedField
                id="tour-step-place"
                name="place"
                data-cy="place"
                label={translate('xploraAdminApp.tourStep.place')}
                type="select"
                required
              >
                <option value="" key="0" />
                {places
                  ? places.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.code}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/tour-step" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default TourStepUpdate;
