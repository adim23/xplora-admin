import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './tour-schedule.reducer';

export const TourScheduleDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const tourScheduleEntity = useAppSelector(state => state.tourSchedule.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="tourScheduleDetailsHeading">
          <Translate contentKey="xploraAdminApp.tourSchedule.detail.title">TourSchedule</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{tourScheduleEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="xploraAdminApp.tourSchedule.code">Code</Translate>
            </span>
          </dt>
          <dd>{tourScheduleEntity.code}</dd>
          <dt>
            <span id="startDatetime">
              <Translate contentKey="xploraAdminApp.tourSchedule.startDatetime">Start Datetime</Translate>
            </span>
          </dt>
          <dd>
            {tourScheduleEntity.startDatetime ? (
              <TextFormat value={tourScheduleEntity.startDatetime} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="noPassengers">
              <Translate contentKey="xploraAdminApp.tourSchedule.noPassengers">No Passengers</Translate>
            </span>
          </dt>
          <dd>{tourScheduleEntity.noPassengers}</dd>
          <dt>
            <span id="noKids">
              <Translate contentKey="xploraAdminApp.tourSchedule.noKids">No Kids</Translate>
            </span>
          </dt>
          <dd>{tourScheduleEntity.noKids}</dd>
          <dt>
            <span id="noPets">
              <Translate contentKey="xploraAdminApp.tourSchedule.noPets">No Pets</Translate>
            </span>
          </dt>
          <dd>{tourScheduleEntity.noPets}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="xploraAdminApp.tourSchedule.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {tourScheduleEntity.createdDate ? (
              <TextFormat value={tourScheduleEntity.createdDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="xploraAdminApp.tourSchedule.createdBy">Created By</Translate>
          </dt>
          <dd>{tourScheduleEntity.createdBy ? tourScheduleEntity.createdBy.login : ''}</dd>
          <dt>
            <Translate contentKey="xploraAdminApp.tourSchedule.tour">Tour</Translate>
          </dt>
          <dd>{tourScheduleEntity.tour ? tourScheduleEntity.tour.code : ''}</dd>
          <dt>
            <Translate contentKey="xploraAdminApp.tourSchedule.vehicle">Vehicle</Translate>
          </dt>
          <dd>{tourScheduleEntity.vehicle ? tourScheduleEntity.vehicle.plate : ''}</dd>
          <dt>
            <Translate contentKey="xploraAdminApp.tourSchedule.driver">Driver</Translate>
          </dt>
          <dd>{tourScheduleEntity.driver ? tourScheduleEntity.driver.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/tour-schedule" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/tour-schedule/${tourScheduleEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TourScheduleDetail;
